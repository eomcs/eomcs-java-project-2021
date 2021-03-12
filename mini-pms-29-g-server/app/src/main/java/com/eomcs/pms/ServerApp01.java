package com.eomcs.pms;

import java.net.ServerSocket;
import java.util.HashMap;
import com.eomcs.pms.table.BoardTable;
import com.eomcs.pms.table.DataTable;
import com.eomcs.pms.table.MemberTable;
import com.eomcs.pms.table.ProjectTable;
import com.eomcs.pms.table.TaskTable;

// 1) 외부의 스레드 사용
public class ServerApp01 {

  int port;
  HashMap<String,DataTable> tableMap = new HashMap<>();

  public static void main(String[] args) {
    ServerApp01 app = new ServerApp01(8888);
    app.service();
  }

  public ServerApp01(int port) {
    this.port = port;
  }

  public void service() {

    // 요청을 처리할 테이블 객체를 준비한다.
    tableMap.put("board/", new BoardTable());
    tableMap.put("member/", new MemberTable());
    tableMap.put("project/", new ProjectTable());
    tableMap.put("task/", new TaskTable());

    // 클라이언트 연결을 기다리는 서버 소켓 생성
    try (ServerSocket serverSocket = new ServerSocket(this.port)) {

      System.out.println("서버 실행!");

      while (true) {
        new StatementHandlerThread(serverSocket.accept(), tableMap).start();
        // 스레드를 시작시키면 현재 실행 흐름과 별개로 새 실행 흐름이 만들어진다.
        // 그 흐름에서 스레드의 run() 메서드를 호출된다.
        // 스레드를 시작시킨 후 즉시 리턴하기 때문에 
        // 대기열에 기다리고 있는 다음 클라이언트의 연결을 바로 승인할 수 있다.
      }

    } catch (Exception e) {
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

}
