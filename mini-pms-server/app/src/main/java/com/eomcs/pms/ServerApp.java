package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.MybatisDaoFactory;
import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.handler.MemberValidator;
import com.eomcs.pms.service.BoardService;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;
import com.eomcs.pms.service.impl.DefaultBoardService;
import com.eomcs.pms.service.impl.DefaultMemberService;
import com.eomcs.pms.service.impl.DefaultProjectService;
import com.eomcs.pms.service.impl.DefaultTaskService;

public class ServerApp {

  int port;

  // 서버의 상태를 설정
  boolean isStop;

  // 객체를 보관할 컨테이너 준비
  Map<String,Object> objMap = new HashMap<>();

  public static void main(String[] args) {

    try {
      ServerApp app = new ServerApp(8888);
      app.service();

    } catch (Exception e) {
      System.out.println("서버를 시작하는 중에 오류 발생!");
      e.printStackTrace();
    }
  }

  public ServerApp(int port) {
    this.port = port;
  }

  public void service() throws Exception {

    // 스레드풀 준비
    ExecutorService threadPool = Executors.newCachedThreadPool();

    // 1) Mybatis 프레임워크 관련 객체 준비
    // => Mybatis 설정 파일을 읽을 입력 스트림 객체 준비
    InputStream mybatisConfigStream = Resources.getResourceAsStream(
        "com/eomcs/pms/conf/mybatis-config.xml");

    // => SqlSessionFactory 객체 준비
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(mybatisConfigStream);

    // => DAO가 사용할 SqlSession 객체 준비
    //    - 수동 commit 으로 동작하는 SqlSession 객체를 준비한다.
    SqlSession sqlSession = sqlSessionFactory.openSession(false);

    // 2) DAO 구현체를 자동으로 만들어주는 공장 객체를 준비한다.
    MybatisDaoFactory daoFactory = new MybatisDaoFactory(sqlSession);

    // 3) 서비스 객체가 사용할 DAO 객체 준비
    BoardDao boardDao = daoFactory.createDao(BoardDao.class);
    MemberDao memberDao = daoFactory.createDao(MemberDao.class);
    ProjectDao projectDao = daoFactory.createDao(ProjectDao.class);
    TaskDao taskDao = daoFactory.createDao(TaskDao.class);

    // 4) Command 구현체가 사용할 의존 객체(서비스 객체 + 도우미 객체) 준비
    // => 서비스 객체 생성
    BoardService boardService = new DefaultBoardService(sqlSession, boardDao);
    MemberService memberService = new DefaultMemberService(sqlSession, memberDao);
    ProjectService projectService = new DefaultProjectService(sqlSession, projectDao, taskDao);
    TaskService taskService = new DefaultTaskService(sqlSession, taskDao);

    // => 도우미 객체 생성
    MemberValidator memberValidator = new MemberValidator(memberService);

    // => Command 구현체가 사용할 의존 객체 보관
    objMap.put("boardService", boardService);
    objMap.put("memberService", memberService);
    objMap.put("projectService", projectService);
    objMap.put("taskService", taskService);
    objMap.put("memberValidator", memberValidator);

    // Command 구현체를 자동 생성하여 맵에 등록
    registerCommands();


    // 클라이언트 연결을 기다리는 서버 소켓 생성
    try (ServerSocket serverSocket = new ServerSocket(this.port)) {

      System.out.println("서버 실행!");

      while (true) {
        Socket socket = serverSocket.accept();
        if (isStop) { // 서버의 상태가 종료이면,
          break; // 즉시 반복문을 탈출하여 main 스레드의 실행을 끝낸다.
        }

        threadPool.execute(() -> processRequest(socket));
      }

    } catch (Exception e) {
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }

    // 스레드풀에 대기하고 있는 모든 스레드를 종료시킨다.
    // => 단 현재 실행 중인 스레드에 대해서는 작업을 완료한 후 종료하도록 설정한다.
    threadPool.shutdown();
    System.out.println("서버 종료 중...");

    // 만약 현재 실행 중인 스레드를 강제로 종료시키고 싶다면 
    // 다음 코드를 참고하라!
    try {
      if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
        System.out.println("아직 실행 중인 스레드가 있습니다.");

        // 종료를 재시도 한다.
        // => 대기 중인 작업도 취소한다.
        // => 실행 중인 스레드 중에서 Not Runnable 상태에 있을 경우에도 강제로 종료시킨다.
        threadPool.shutdownNow();

        while (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
          System.out.println("아직 실행 중인 스레드가 있습니다.");
        } 

        System.out.println("모든 스레드를 종료했습니다.");
      }
    } catch (Exception e) {
      System.out.println("스레드 강제 종료 중에 오류 발생!");
    }

    System.out.println("서버 종료!");
  }

  public void processRequest(Socket socket) {
    try (
        Socket clientSocket = socket;
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
        ) {

      while (true) {
        // 클라이언트가 보낸 요청을 읽는다.
        String requestLine = in.readLine();

        if (requestLine.equalsIgnoreCase("serverstop")) {
          in.readLine(); // 요청의 끝을 의미하는 빈 줄을 읽는다.
          out.println("Server stopped!");
          out.println();
          out.flush();
          terminate();
          return; 
        }

        if (requestLine.equalsIgnoreCase("exit") || requestLine.equalsIgnoreCase("quit")) {
          in.readLine(); // 요청의 끝을 의미하는 빈 줄을 읽는다.
          out.println("Goodbye!");
          out.println();
          out.flush();
          return;
        }

        // 클라이언트 보낸 명령을 서버 창에 출력한다.
        System.out.println(requestLine);

        // 클라이언트가 보낸 데이터를 읽는다.
        while (true) {
          String line = in.readLine();
          if (line.length() == 0) {
            break;
          }
          // 클라이언트에서 보낸 데이터를 서버 창에 출력해 보자.
          System.out.println(line);
        }
        System.out.println("------------------------------------");

        // 클라이언트에게 응답한다.
        out.println("OK");
        out.printf("====> %s\n", requestLine);   
        out.println();
        out.flush();
      }

    } catch (Exception e) {
      System.out.println("클라이언트의 요청을 처리하는 중에 오류 발생!");
      e.printStackTrace();
    }
  }

  // 서버를 최종적으로 종료하는 일을 한다.
  private void terminate() {
    // 서버 상태를 종료로 설정한다.
    isStop = true;

    // 그리고 서버가 즉시 종료할 수 있도록 임의의 접속을 수행한다.
    // => 스스로 클라이언트가 되어 ServerSocket 에 접속하면 
    //    accept()에서 리턴하기 때문에 isStop 변수의 상태에 따라 반복문을 멈출 것이다.
    try (Socket socket = new Socket("localhost", 8888)) {
      // 서버를 종료시키기 위해 임의로 접속하는 것이기 때문에 특별히 추가로 해야 할 일이 없다.
    } catch (Exception e) {}
  }

}
