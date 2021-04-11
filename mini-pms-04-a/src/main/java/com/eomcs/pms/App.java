package com.eomcs.pms;

// 1) 프롬프트 관련 메서드를 별도로 분류한다.
//    - Prompt 클래스를 생성한다.
//    - promptString(), promptInt(), promptDate() 메서드를 옮긴다.
//    - 메서드 이름을 변경한다.
//      - promptString() ==> inputString()
//      - promptInt() ==> inputInt()
//      - promptDate() ==> inputDate()
//    - Prompt 클래스의 메서드들이 사용하는 변수도 옮긴다.
//      - keyboardScan 변수를 옮긴다.
// 2) 회원 데이터를 다루는 메서드를 별도로 분류한다.
//    - MemberHandler 클래스를 생성한다.
//    - addMember(), listMember() 메서드를 옮긴다.
//    - MemberHandler의 메서드들이 사용할 변수를 App에서 옮겨 온다.
//    - 메서드의 이름을 변경한다.
//      - addMember() ==> add()
//      - listMember() ==> list();
// 3) 프로젝트 데이터를 다루는 메서드를 별도로 분류한다.
//    - ProjectHandler 클래스를 생성한다.
//    - addProject(), listProject() 메서드를 옮긴다.
//    - ProjectHandler의 메서드들이 사용할 변수를 App에서 옮겨 온다.
//    - 메서드의 이름을 변경한다.
//      - addProject() ==> add()
//      - listProject() ==> list();
// 4) 작업 데이터를 다루는 메서드를 별도로 분류한다.
//    - TaskHandler 클래스를 생성한다.
//    - addTask(), listTask() 메서드를 옮긴다.
//    - TaskHandler의 메서드들이 사용할 변수를 App에서 옮겨 온다.
//    - 메서드의 이름을 변경한다.
//      - addTask() ==> add()
//      - listTask() ==> list();
// 5) 다른 클래스에 소속된 값은 그 클래스에게 맡긴다.
//    - Prompt 클래스에 close() 메서드를 추가한다.
//    - App 클래스는 Prompt의 close()를 호출하여 키보드 스캐너를 닫는다.
public class App {

  public static void main(String[] args) {

    loop:
      while (true) {
        String command = Prompt.inputString("명령> ");

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
