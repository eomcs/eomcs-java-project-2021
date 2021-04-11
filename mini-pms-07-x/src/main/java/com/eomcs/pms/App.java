package com.eomcs.pms;

import com.eomcs.pms.handler.BoardHandler;
import com.eomcs.pms.handler.MemberHandler;
import com.eomcs.pms.handler.ProjectHandler;
import com.eomcs.pms.handler.TaskHandler;
import com.eomcs.util.Prompt;

public class App {

  public static void main(String[] args) {

    BoardHandler boardHandler = new BoardHandler();
    MemberHandler memberHandler = new MemberHandler();
    ProjectHandler projectHandler = new ProjectHandler(memberHandler);
    TaskHandler taskHandler = new TaskHandler(memberHandler);


    loop:
      while (true) {
        System.out.println("메인 --------------------------------");
        System.out.println("1. 게시판");
        System.out.println("2. 회원");
        System.out.println("3. 프로젝트");
        System.out.println("4. 작업");
        System.out.println("0. 종료");

        String command = com.eomcs.util.Prompt.inputString("메인> ");
        System.out.println();

        switch (command) {
          case "1":
            boardHandler.service();
            break;
          case "2":
            memberHandler.service();
            break;
          case "3":
            projectHandler.service();
            break;
          case "4":
            taskHandler.service();
            break;
          case "0":
            System.out.println("안녕!");
            break loop;
          default:
            System.out.println("메뉴 번호가 맞지 않습니다.");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }

    Prompt.close();
  }
}
