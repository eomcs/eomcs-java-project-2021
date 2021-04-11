package com.eomcs.pms;

import com.eomcs.pms.handler.MemberHandler;
import com.eomcs.pms.handler.ProjectHandler;
import com.eomcs.pms.handler.TaskHandler;
import com.eomcs.util.Prompt;

// 1) Prompt 클래스를 별도의 패키지로 분류한다
//    - com.eomcs.util 패키지 생성
//    - Prompt 클래스를 이 패키지로 옮긴다.
//    - Prompt 클래스를 다른 패키지의 클래스가 사용할 수 있도록 public 으로 공개한다.
//    - Prompt의 메서드를 다른 패키지의 클래스가 사용할 수 있도록 public 으로 공개한다.
// 2) 핸들러 클래스들을 별도의 패키지로 분류한다
//    - com.eomcs.pms.handler 패키지 생성
//    - XxxHandler 클래스를 이 패키지로 옮긴다.
//    - 핸들러 클래스를 다른 패키지의 클래스가 사용할 수 있도록 public 으로 공개한다.
//    - 핸들러의 메서드를 다른 패키지의 클래스가 사용할 수 있도록 public 으로 공개한다.
public class App {

  public static void main(String[] args) {

    loop:
      while (true) {
        String command = com.eomcs.util.Prompt.inputString("명령> ");

        switch (command) {
          case "/member/add":
            MemberHandler.add();
            break;
          case "/member/list":
            MemberHandler.list();
            break;
          case "/project/add":
            ProjectHandler.add();
            break;
          case "/project/list":
            ProjectHandler.list();
            break;
          case "/task/add":
            TaskHandler.add();
            break;
          case "/task/list":
            TaskHandler.list();
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
