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
//
public class App_a {

  // 회원 데이터
  static final int LENGTH = 100;
  static int[] no = new int[LENGTH];
  static String[] name = new String[LENGTH];
  static String[] email = new String[LENGTH];
  static String[] password = new String[LENGTH];
  static String[] photo = new String[LENGTH];
  static String[] tel = new String[LENGTH];
  static Date[] registeredDate = new Date[LENGTH];  
  static int size = 0;

  // 프로젝트 데이터
  static final int PLENGTH = 100;
  static int[] pno = new int[PLENGTH];
  static String[] ptitle = new String[PLENGTH];
  static String[] pcontent = new String[PLENGTH];
  static Date[] pstartDate = new Date[PLENGTH];
  static Date[] pendDate = new Date[PLENGTH];
  static String[] powner = new String[PLENGTH];
  static String[] pmembers = new String[PLENGTH];  
  static int psize = 0;

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
            addMember();
            break;
          case "/member/list":
            listMember();
            break;
          case "/project/add":
            addProject();
            break;
          case "/project/list":
            listProject();
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

  static void addMember() {
    System.out.println("[회원 등록]");

    no[size] = Prompt.inputInt("번호? ");
    name[size] = Prompt.inputString("이름? ");
    email[size] = Prompt.inputString("이메일? ");
    password[size] = Prompt.inputString("암호? ");
    photo[size] = Prompt.inputString("사진? ");
    tel[size] = Prompt.inputString("전화? ");
    registeredDate[size] = new java.sql.Date(System.currentTimeMillis());
    size++;
  }

  static void listMember() {
    System.out.println("[회원 목록]");

    for (int i = 0; i < size; i++) {
      // 번호, 이름, 이메일, 전화, 가입일
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          no[i], name[i], email[i], tel[i], registeredDate[i]);
    }
  }

  static void addProject() {
    System.out.println("[프로젝트 등록]");

    pno[psize] = Prompt.inputInt("번호? ");
    ptitle[psize] = Prompt.inputString("프로젝트명? ");
    pcontent[psize] = Prompt.inputString("내용? ");
    pstartDate[psize] = Prompt.inputDate("시작일? ");
    pendDate[psize] = Prompt.inputDate("종료일? ");
    powner[psize] = Prompt.inputString("만든이? ");
    pmembers[psize] = Prompt.inputString("팀원? ");

    psize++;
  }

  static void listProject() {
    System.out.println("[프로젝트 목록]");

    for (int i = 0; i < psize; i++) {
      // 번호, 프로젝트명, 시작일, 종료일, 만든이
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          pno[i], ptitle[i], pstartDate[i], pendDate[i], powner[i]);
    }
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
