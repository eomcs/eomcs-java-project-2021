package com.eomcs.pms;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import com.eomcs.pms.handler.BoardAddHandler;
import com.eomcs.pms.handler.BoardDeleteHandler;
import com.eomcs.pms.handler.BoardDetailHandler;
import com.eomcs.pms.handler.BoardListHandler;
import com.eomcs.pms.handler.BoardSearchHandler;
import com.eomcs.pms.handler.BoardUpdateHandler;
import com.eomcs.pms.handler.Command;
import com.eomcs.util.Prompt;

public class ClientApp {

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  ArrayDeque<String> commandStack = new ArrayDeque<>();
  LinkedList<String> commandQueue = new LinkedList<>();

  String serverAddress;
  int port;

  public static void main(String[] args) {
    ClientApp app = new ClientApp("localhost", 8888);

    try {
      app.execute();

    } catch (Exception e) {
      System.out.println("클라이언트 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

  public ClientApp(String serverAddress, int port) {
    this.serverAddress = serverAddress;
    this.port = port;
  }

  public void execute() throws Exception {

    // 사용자 명령을 처리하는 객체를 맵에 보관한다.
    HashMap<String,Command> commandMap = new HashMap<>();

    commandMap.put("/board/add", new BoardAddHandler());
    commandMap.put("/board/list", new BoardListHandler());
    commandMap.put("/board/detail", new BoardDetailHandler());
    commandMap.put("/board/update", new BoardUpdateHandler());
    commandMap.put("/board/delete", new BoardDeleteHandler());
    commandMap.put("/board/search", new BoardSearchHandler());
    //
    //    commandMap.put("/member/add", new MemberAddHandler(stmt));
    //    commandMap.put("/member/list", new MemberListHandler(stmt));
    //    commandMap.put("/member/detail", new MemberDetailHandler(stmt));
    //    commandMap.put("/member/update", new MemberUpdateHandler(stmt));
    //    commandMap.put("/member/delete", new MemberDeleteHandler(stmt));
    //
    //    MemberValidator memberValidator = new MemberValidator(stmt);
    //
    //    commandMap.put("/project/add", new ProjectAddHandler(stmt, memberValidator));
    //    commandMap.put("/project/list", new ProjectListHandler(stmt));
    //    commandMap.put("/project/detail", new ProjectDetailHandler(stmt));
    //    commandMap.put("/project/update", new ProjectUpdateHandler(stmt, memberValidator));
    //    commandMap.put("/project/delete", new ProjectDeleteHandler(stmt));
    //
    //    commandMap.put("/task/add", new TaskAddHandler(stmt, memberValidator));
    //    commandMap.put("/task/list", new TaskListHandler(stmt));
    //    commandMap.put("/task/detail", new TaskDetailHandler(stmt));
    //    commandMap.put("/task/update", new TaskUpdateHandler(stmt, memberValidator));
    //    commandMap.put("/task/delete", new TaskDeleteHandler(stmt));

    try {

      while (true) {

        String command = com.eomcs.util.Prompt.inputString("명령> ");

        if (command.length() == 0) {
          continue;
        }

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
              return;
            default:
              Command commandHandler = commandMap.get(command);

              if (commandHandler == null) {
                System.out.println("실행할 수 없는 명령입니다.");
              } else {
                commandHandler.service();
              }
          }
        } catch (Exception e) {
          System.out.println("------------------------------------------");
          System.out.printf("명령어 실행 중 오류 발생: %s\n", e.getMessage());
          System.out.println("------------------------------------------");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }

    } catch (Exception e) {
      System.out.println("서버와 통신 하는 중에 오류 발생!");
    }

    Prompt.close();
  }

  private void printCommandHistory(Iterator<String> iterator) {
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
}
