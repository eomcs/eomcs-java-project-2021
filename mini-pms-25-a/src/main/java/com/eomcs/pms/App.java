package com.eomcs.pms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.handler.BoardAddHandler;
import com.eomcs.pms.handler.BoardDeleteHandler;
import com.eomcs.pms.handler.BoardDetailHandler;
import com.eomcs.pms.handler.BoardListHandler;
import com.eomcs.pms.handler.BoardSearchHandler;
import com.eomcs.pms.handler.BoardUpdateHandler;
import com.eomcs.pms.handler.Command;
import com.eomcs.pms.handler.HelloHandler;
import com.eomcs.pms.handler.MemberAddHandler;
import com.eomcs.pms.handler.MemberDeleteHandler;
import com.eomcs.pms.handler.MemberDetailHandler;
import com.eomcs.pms.handler.MemberListHandler;
import com.eomcs.pms.handler.MemberUpdateHandler;
import com.eomcs.pms.handler.MemberValidatorHandler;
import com.eomcs.pms.handler.ProjectAddHandler;
import com.eomcs.pms.handler.ProjectDeleteHandler;
import com.eomcs.pms.handler.ProjectDetailHandler;
import com.eomcs.pms.handler.ProjectListHandler;
import com.eomcs.pms.handler.ProjectUpdateHandler;
import com.eomcs.pms.handler.TaskAddHandler;
import com.eomcs.pms.handler.TaskDeleteHandler;
import com.eomcs.pms.handler.TaskDetailHandler;
import com.eomcs.pms.handler.TaskListHandler;
import com.eomcs.pms.handler.TaskUpdateHandler;
import com.eomcs.util.Prompt;

//1) 게시글 데이터 로딩 및 저장 (메서드로 분리하기 전)
//2) 게시글 데이터 로딩 및 저장 (메서드로 분리)
//3) 회원 데이터 로딩 및 저장
//4) 프로젝트 데이터 로딩 및 저장
//5) 작업 데이터 로딩 및 저장
public class App {

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  static ArrayDeque<String> commandStack = new ArrayDeque<>();
  static LinkedList<String> commandQueue = new LinkedList<>();

  // VO 를 저장할 컬렉션 객체
  static ArrayList<Board> boardList = new ArrayList<>();
  static ArrayList<Member> memberList = new ArrayList<>();
  static LinkedList<Project> projectList = new LinkedList<>();
  static LinkedList<Task> taskList = new LinkedList<>();

  public static void main(String[] args) {


    // 파일에서 데이터를 읽어온다.(데이터 로딩)
    loadBoards();
    loadMembers();
    loadProjects();
    loadTasks();

    // 사용자 명령을 처리하는 객체를 맵에 보관한다.
    HashMap<String,Command> commandMap = new HashMap<>();

    commandMap.put("/board/add", new BoardAddHandler(boardList));
    commandMap.put("/board/list", new BoardListHandler(boardList));
    commandMap.put("/board/detail", new BoardDetailHandler(boardList));
    commandMap.put("/board/update", new BoardUpdateHandler(boardList));
    commandMap.put("/board/delete", new BoardDeleteHandler(boardList));

    commandMap.put("/member/add", new MemberAddHandler(memberList));
    commandMap.put("/member/list", new MemberListHandler(memberList));
    commandMap.put("/member/detail", new MemberDetailHandler(memberList));
    commandMap.put("/member/update", new MemberUpdateHandler(memberList));
    commandMap.put("/member/delete", new MemberDeleteHandler(memberList));
    MemberValidatorHandler memberValidatorHandler = new MemberValidatorHandler(memberList);

    commandMap.put("/project/add", new ProjectAddHandler(projectList, memberValidatorHandler));
    commandMap.put("/project/list", new ProjectListHandler(projectList));
    commandMap.put("/project/detail", new ProjectDetailHandler(projectList));
    commandMap.put("/project/update", new ProjectUpdateHandler(projectList, memberValidatorHandler));
    commandMap.put("/project/delete", new ProjectDeleteHandler(projectList));

    commandMap.put("/task/add", new TaskAddHandler(taskList, memberValidatorHandler));
    commandMap.put("/task/list", new TaskListHandler(taskList));
    commandMap.put("/task/detail", new TaskDetailHandler(taskList));
    commandMap.put("/task/update", new TaskUpdateHandler(taskList, memberValidatorHandler));
    commandMap.put("/task/delete", new TaskDeleteHandler(taskList));

    // 새 기능 추가
    commandMap.put("/board/search", new BoardSearchHandler(boardList));
    commandMap.put("/hello", new HelloHandler());

    loop:
      while (true) {
        String command = com.eomcs.util.Prompt.inputString("명령> ");

        if (command.length() == 0) // 사용자가 빈 문자열을 입력하면 다시 입력하도록 요구한다.
          continue;

        // 사용자가 입력한 명령을 보관해둔다.
        commandStack.push(command);
        commandQueue.offer(command);

        try {
          switch (command) {
            case "history":
              printCommandHistory(commandStack.iterator());
              break;
            case "history2": 
              printCommandHistory(commandQueue.iterator());
              break;
            case "quit":
            case "exit":
              System.out.println("안녕!");
              break loop;
            default:
              Command commandHandler = commandMap.get(command);

              if (commandHandler == null) {
                System.out.println("실행할 수 없는 명령입니다.");
              } else {
                commandHandler.service();
                // 이제 명령어와 그 명령어를 처리하는 핸들러를 추가할 때 마다
                // case 문을 추가할 필요가 없다.
              }
          }
        } catch (Exception e) {
          System.out.println("------------------------------------------");
          System.out.printf("명령어 실행 중 오류 발생: %s - %s\n", 
              e.getClass().getName(), e.getMessage());
          System.out.println("------------------------------------------");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }

    // 게시글 데이터를 파일로 출력한다.
    saveBoards();
    saveMembers();
    saveProjects();
    saveTasks();

    Prompt.close();
  }

  static void printCommandHistory(Iterator<String> iterator) {
    int count = 0;
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
      if ((++count % 5) == 0) {
        String input = Prompt.inputString(": ");
        if (input.equalsIgnoreCase("q")) {
          break;
        }
      }
    }
  }

  static void loadBoards() {
    try (FileInputStream in = new FileInputStream("boards.data")) {
      // boards.data 파일 포맷에 따라 데이터를 읽는다.
      // 1) 게시글 개수
      int size = in.read() << 8 | in.read();

      // 2) 게시글 개수 만큼 게시글을 읽는다.
      for (int i = 0; i < size; i++) {
        // 게시글 데이터를 저장할 객체 준비
        Board b = new Board();

        // 게시글 데이터를 읽어서 객체에 저장
        // - 게시글 번호를 읽어서 객체에 저장
        b.setNo(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        // - 게시글 제목을 읽어서 객체에 저장
        int len = in.read() << 8 | in.read();
        byte[] buf = new byte[len];
        in.read(buf);
        b.setTitle(new String(buf, "UTF-8"));

        // - 게시글 내용을 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        b.setContent(new String(buf, "UTF-8"));

        // - 게시글 작성자 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        b.setWriter(new String(buf, "UTF-8"));

        // - 게시글 등록일을 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        b.setRegisteredDate(Date.valueOf(new String(buf, "UTF-8")));

        // - 게시글 조회수를 읽어서 객체에 저장
        b.setViewCount(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        // 게시글 객체를 컬렉션에 저장
        boardList.add(b);
      }
      System.out.println("게시글 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("게시글 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveBoards() {
    try (FileOutputStream out = new FileOutputStream("boards.data")) {

      // boards.data 파일 포맷
      // - 2바이트: 저장된 게시글 개수
      // - 게시글 데이터 목록
      //   - 4바이트: 게시글 번호
      //   - 게시글 제목
      //     - 2바이트: 게시글 제목의 바이트 배열 개수
      //     - x바이트: 게시글 제목의 바이트 배열
      //   - 게시글 내용
      //     - 2바이트: 게시글 내용의 바이트 배열 개수
      //     - x바이트: 게시글 내용의 바이트 배열
      //   - 작성자
      //     - 2바이트: 작성자의 바이트 배열 개수
      //     - x바이트: 작성자의 바이트 배열
      //   - 등록일
      //     - 2바이트: 등록일의 바이트 배열 개수
      //     - x바이트: 등록일의 바이트 배열
      //   - 4바이트: 조회수
      int size = boardList.size();
      out.write(size >> 8);
      out.write(size);

      for (Board b : boardList) {
        // 게시글 번호
        out.write(b.getNo() >> 24);
        out.write(b.getNo() >> 16);
        out.write(b.getNo() >> 8);
        out.write(b.getNo());

        // 게시글 제목
        byte[] buf = b.getTitle().getBytes("UTF-8");
        // - 게시글 제목의 바이트 개수
        out.write(buf.length >> 8);
        out.write(buf.length);
        // - 게시글 제목의 바이트 배열
        out.write(buf);

        // 게시글 내용
        buf = b.getContent().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 작성자
        buf = b.getWriter().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 등록일
        buf = b.getRegisteredDate().toString().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 조회수
        out.write(b.getViewCount() >> 24);
        out.write(b.getViewCount() >> 16);
        out.write(b.getViewCount() >> 8);
        out.write(b.getViewCount());
      }
      System.out.println("게시글 데이터 저장!");

    } catch (Exception e) {
      System.out.println("게시글 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadMembers() {

    try (FileInputStream in = new FileInputStream("members.data")) {
      // 데이터의 개수를 먼저 읽는다. (2바이트)
      int size = in.read() << 8 | in.read();

      for (int i = 0; i < size; i++) {
        // 데이터를 담을 객체 준비
        Member member = new Member();

        // 출력 형식에 맞춰서 파일에서 데이터를 읽는다.
        // => 회원 번호 읽기
        int value = in.read() << 24;
        value += in.read() << 16;
        value += in.read() << 8;
        value += in.read();
        member.setNo(value);

        // 문자열을 읽을 바이트 배열을 준비한다.
        byte[] bytes = new byte[30000];

        // => 회원 이름 읽기
        int len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        member.setName(new String(bytes, 0, len, "UTF-8"));

        // => 회원 이메일 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        member.setEmail(new String(bytes, 0, len, "UTF-8"));

        // => 회원 암호 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        member.setPassword(new String(bytes, 0, len, "UTF-8"));

        // => 회원 사진 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        member.setPhoto(new String(bytes, 0, len, "UTF-8"));

        // => 회원 전화 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        member.setTel(new String(bytes, 0, len, "UTF-8"));

        // => 회원 등록일 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, 10);
        member.setRegisteredDate(Date.valueOf(new String(bytes, 0, 10, "UTF-8")));

        memberList.add(member);
      }
      System.out.println("회원 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("회원 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveMembers() {

    try (FileOutputStream out = new FileOutputStream("members.data")) {

      // 데이터의 개수를 먼저 출력한다.(2바이트)
      out.write(memberList.size() >> 8);
      out.write(memberList.size());

      for (Member member : memberList) {
        // 회원 목록에서 회원 데이터를 꺼내 바이너리 형식으로 출력한다.
        // => 회원 번호 출력 (4바이트)
        out.write(member.getNo() >> 24);
        out.write(member.getNo() >> 16);
        out.write(member.getNo() >> 8);
        out.write(member.getNo());

        // => 회원 이름 
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        byte[] bytes = member.getName().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 회원 이메일 
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = member.getEmail().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 회원 암호 
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = member.getPassword().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 회원 사진 
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = member.getPhoto().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 회원 전화 
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = member.getTel().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 회원 등록일
        //      문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = member.getRegisteredDate().toString().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);
      }
      System.out.println("회원 데이터 저장!");

    } catch (Exception e) {
      System.out.println("회원 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadProjects() {

    try (FileInputStream in = new FileInputStream("projects.data")) {

      // 데이터의 개수를 먼저 읽는다. (2바이트)
      int size = in.read() << 8 | in.read();

      for (int i = 0; i < size; i++) {
        // 데이터를 담을 객체 준비
        Project project = new Project();

        // 출력 형식에 맞춰서 파일에서 데이터를 읽는다.
        // => 프로젝트 번호 읽기
        int value = in.read() << 24;
        value += in.read() << 16;
        value += in.read() << 8;
        value += in.read();
        project.setNo(value);

        // 문자열을 읽을 바이트 배열을 준비한다.
        byte[] bytes = new byte[30000];

        // => 프로젝트 제목 읽기
        int len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        project.setTitle(new String(bytes, 0, len, "UTF-8"));

        // => 프로젝트 내용 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        project.setContent(new String(bytes, 0, len, "UTF-8"));

        // => 프로젝트 시작일 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        project.setStartDate(Date.valueOf(new String(bytes, 0, 10, "UTF-8")));

        // => 프로젝트 종료일 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        project.setEndDate(Date.valueOf(new String(bytes, 0, 10, "UTF-8")));

        // => 프로젝트 소유주 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        project.setOwner(new String(bytes, 0, len, "UTF-8"));

        // => 프로젝트 멤버들 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        project.setMembers(new String(bytes, 0, len, "UTF-8"));

        projectList.add(project);
      }
      System.out.println("프로젝트 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("프로젝트 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveProjects() {

    try (FileOutputStream out = new FileOutputStream("projects.data")) {

      // 데이터의 개수를 먼저 출력한다.(2바이트)
      out.write(projectList.size() >> 8);
      out.write(projectList.size());

      for (Project project : projectList) {
        // 프로젝트 목록에서 프로젝트 데이터를 꺼내 바이너리 형식으로 출력한다.
        // => 프로젝트 번호 출력 (4바이트)
        out.write(project.getNo() >> 24);
        out.write(project.getNo() >> 16);
        out.write(project.getNo() >> 8);
        out.write(project.getNo());

        // => 프로젝트 제목 
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        byte[] bytes = project.getTitle().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 프로젝트 내용
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = project.getContent().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 프로젝트 시작일
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = project.getStartDate().toString().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 프로젝트 종료일 
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = project.getEndDate().toString().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 프로젝트 소유주
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = project.getOwner().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 프로젝트 멤버들
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = project.getMembers().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);
      }
      System.out.println("프로젝트 데이터 저장!");

    } catch (Exception e) {
      System.out.println("프로젝트 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadTasks() {

    try (FileInputStream in = new FileInputStream("tasks.data")) {

      // 데이터의 개수를 먼저 읽는다. (2바이트)
      int size = in.read() << 8 | in.read();

      for (int i = 0; i < size; i++) {
        // 데이터를 담을 객체 준비
        Task task = new Task();

        // 출력 형식에 맞춰서 파일에서 데이터를 읽는다.
        // => 작업 번호 읽기
        int value = in.read() << 24;
        value += in.read() << 16;
        value += in.read() << 8;
        value += in.read();
        task.setNo(value);

        // 문자열을 읽을 바이트 배열을 준비한다.
        byte[] bytes = new byte[30000];

        // => 작업 내용 읽기
        int len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        task.setContent(new String(bytes, 0, len, "UTF-8"));

        // => 작업 종료일 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        task.setDeadline(Date.valueOf(new String(bytes, 0, 10, "UTF-8")));

        // => 작업 상태 읽기
        value = in.read() << 24;
        value += in.read() << 16;
        value += in.read() << 8;
        value += in.read();
        task.setStatus(value);

        // => 작업 소유주 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        task.setOwner(new String(bytes, 0, len, "UTF-8"));

        taskList.add(task);
      }
      System.out.println("작업 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("작업 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveTasks() {

    try (FileOutputStream out = new FileOutputStream("tasks.data")) {

      // 데이터의 개수를 먼저 출력한다.(2바이트)
      out.write(taskList.size() >> 8);
      out.write(taskList.size());

      for (Task task : taskList) {
        // 작업 목록에서 작업 데이터를 꺼내 바이너리 형식으로 출력한다.
        // => 작업 번호 출력 (4바이트)
        out.write(task.getNo() >> 24);
        out.write(task.getNo() >> 16);
        out.write(task.getNo() >> 8);
        out.write(task.getNo());

        // => 작업 내용 
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        byte[] bytes = task.getContent().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 작업 종료일(10바이트)
        bytes = task.getDeadline().toString().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 작업 상태 출력 (4바이트)
        out.write(task.getStatus() >> 24);
        out.write(task.getStatus() >> 16);
        out.write(task.getStatus() >> 8);
        out.write(task.getStatus());

        // => 작업 소유주
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = task.getOwner().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);
      }
      System.out.println("작업 데이터 저장!");

    } catch (Exception e) {
      System.out.println("작업 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }
}
