package com.eomcs.pms;

import java.sql.Date;

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
//
public class App_c {

  // 작업 데이터
  static final int TLENGTH = 100;
  static int[] tno = new int[TLENGTH];
  static String[] tcontent = new String[TLENGTH];
  static Date[] tdeadline = new Date[TLENGTH];
  static String[] towner = new String[TLENGTH];
  static int[] tstatus = new int[TLENGTH];
  static int tsize = 0;

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
            addTask();
            break;
          case "/task/list":
            listTask();
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

    Prompt.keyboardScan.close();
  }

  static void addTask() {
    System.out.println("[작업 등록]");

    tno[tsize] = Prompt.inputInt("번호? ");
    tcontent[tsize] = Prompt.inputString("내용? ");
    tdeadline[tsize] = Prompt.inputDate("마감일? ");
    tstatus[tsize] = Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> ");
    towner[tsize] = Prompt.inputString("담당자? ");

    tsize++;
  }

  static void listTask() {
    System.out.println("[작업 목록]");

    for (int i = 0; i < tsize; i++) {
      String stateLabel = null;
      switch (tstatus[i]) {
        case 1:
          stateLabel = "진행중";
          break;
        case 2:
          stateLabel = "완료";
          break;
        default:
          stateLabel = "신규";
      }
      // 번호, 작업명, 마감일, 프로젝트, 상태, 담당자
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          tno[i], tcontent[i], tdeadline[i], stateLabel, towner[i]);
    }
  }

}
