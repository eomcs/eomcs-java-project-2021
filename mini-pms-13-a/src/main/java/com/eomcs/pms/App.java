package com.eomcs.pms;

import com.eomcs.pms.handler.BoardHandler;
import com.eomcs.pms.handler.MemberHandler;
import com.eomcs.pms.handler.ProjectHandler;
import com.eomcs.pms.handler.TaskHandler;
import com.eomcs.util.Prompt;

public class App {

  public static void main(String[] args) {

    // 각 게시판 데이터를 저장할 메모리 준비
    BoardHandler boardList1 = new BoardHandler();
    BoardHandler boardList2 = new BoardHandler();
    BoardHandler boardList3 = new BoardHandler();
    BoardHandler boardList4 = new BoardHandler();
    BoardHandler boardList5 = new BoardHandler();
    BoardHandler boardList6 = new BoardHandler();

    // 각 회원 목록 데이터를 저장할 메모리 준비
    MemberHandler memberList = new MemberHandler();

    // 각 프로젝트 목록 데이터를 저장할 메모리 준비
    ProjectHandler projectList = new ProjectHandler();

    // ProjectHandler가 의존하는 객체(dependency)를 주입한다.
    // add() 메서드를 호출할 때 마다 파라미터에 넘기는 대신에
    // 계속 사용할 수 있도록 인스턴스 필드에 담아 놓는다.
    projectList.memberList = memberList;

    // 각 작업 목록 데이터를 저장할 메모리 준비
    TaskHandler taskList = new TaskHandler();

    // TaskHandler가 사용할 의존 객체를 주입한다.
    taskList.memberList = memberList;

    loop:
      while (true) {
        String command = com.eomcs.util.Prompt.inputString("명령> ");

        switch (command) {
          case "/member/add":
            memberList.add();
            break;
          case "/member/list":
            memberList.list();
            break;
          case "/project/add":
            projectList.add();
            break;
          case "/project/list":
            projectList.list();
            break;
          case "/task/add":
            taskList.add();
            break;
          case "/task/list":
            taskList.list();
            break;
          case "/board/add":
            boardList1.add();
            break;
          case "/board/list":
            boardList1.list();
            break;
          case "/board2/add":
            boardList2.add();
            break;
          case "/board2/list":
            boardList2.list();
            break;
          case "/board3/add":
            boardList3.add();
            break;
          case "/board3/list":
            boardList3.list();
            break;
          case "/board4/add":
            boardList4.add();
            break;
          case "/board4/list":
            boardList4.list();
            break;
          case "/board5/add":
            boardList5.add();
            break;
          case "/board5/list":
            boardList5.list();
            break;
          case "/board6/add":
            boardList6.add();
            break;
          case "/board6/list":
            boardList6.list();
            break;
          case "quit":
          case "exit":
            System.out.println("안녕!");
            break loop;
          default:
            System.out.println("실행할 수 없는 명령입니다.");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }

    Prompt.close();
  }
}
