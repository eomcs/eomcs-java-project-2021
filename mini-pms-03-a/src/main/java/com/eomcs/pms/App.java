package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

// 1) 명령 프롬프트를 출력한다. 
// 2) 명령어를 입력 받아 출력한다.
// 3) 명령어를 입력 받는 것을 반복한다.
// 4) `/member/add`, `/member/list` 명령을 구분한다.
// 5) `/member/add` 명령을 처리한다.
// 6) `/member/list` 명령을 처리한다.
// 7) `/project/add` 명령을 처리한다.
// 8) `/project/list` 명령을 처리한다.
// 9) `/task/add` 명령을 처리한다.
// 10) `/task/list` 명령을 처리한다
public class App {

  public static void main(String[] args) {
    Scanner keyboardScan = new Scanner(System.in);

    // 회원 데이터
    final int LENGTH = 100;
    int[] no = new int[LENGTH];
    String[] name = new String[LENGTH];
    String[] email = new String[LENGTH];
    String[] password = new String[LENGTH];
    String[] photo = new String[LENGTH];
    String[] tel = new String[LENGTH];
    Date[] registeredDate = new Date[LENGTH];

    int size = 0;
    
    // 프로젝트 데이터
    final int PLENGTH = 100;
    int[] pno = new int[PLENGTH];
    String[] ptitle = new String[PLENGTH];
    String[] pcontent = new String[PLENGTH];
    Date[] pstartDate = new Date[PLENGTH];
    Date[] pendDate = new Date[PLENGTH];
    String[] powner = new String[PLENGTH];
    String[] pmembers = new String[PLENGTH];

    int psize = 0;
    
    // 작업 데이터
    final int TLENGTH = 100;
    
    int[] tno = new int[TLENGTH];
    String[] tcontent = new String[TLENGTH];
    Date[] tdeadline = new Date[TLENGTH];
    String[] towner = new String[TLENGTH];
    int[] tstatus = new int[TLENGTH];
    
    int tsize = 0;
    
    loop:
      while (true) {
        System.out.print("명령> ");
        String command = keyboardScan.nextLine();

        switch (command) {
          case "/member/add":
            System.out.println("[회원 등록]");
            
            System.out.print("번호? ");
            no[size] = Integer.parseInt(keyboardScan.nextLine());

            System.out.print("이름? ");
            name[size] = keyboardScan.nextLine();

            System.out.print("이메일? ");
            email[size] = keyboardScan.nextLine();

            System.out.print("암호? ");
            password[size] = keyboardScan.nextLine();

            System.out.print("사진? ");
            photo[size] = keyboardScan.nextLine();

            System.out.print("전화? ");
            tel[size] = keyboardScan.nextLine();

            registeredDate[size] = new java.sql.Date(System.currentTimeMillis());

            size++;
            break;
          case "/member/list":
            System.out.println("[회원 목록]");
            for (int i = 0; i < size; i++) {
              // 번호, 이름, 이메일, 전화, 가입일
              System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
                  no[i], name[i], email[i], tel[i], registeredDate[i]);
            }
            break;
          case "/project/add":
            System.out.println("[프로젝트 등록]");
            
            System.out.print("번호? ");
            pno[psize] = Integer.valueOf(keyboardScan.nextLine());

            System.out.print("프로젝트명? ");
            ptitle[psize] = keyboardScan.nextLine();

            System.out.print("내용? ");
            pcontent[psize] = keyboardScan.nextLine();

            System.out.print("시작일? ");
            pstartDate[psize] = Date.valueOf(keyboardScan.nextLine());

            System.out.print("종료일? ");
            pendDate[psize] = Date.valueOf(keyboardScan.nextLine());

            System.out.print("만든이? ");
            powner[psize] = keyboardScan.nextLine();

            System.out.print("팀원? ");
            pmembers[psize] = keyboardScan.nextLine();

            psize++;
            break;
          case "/project/list":
            System.out.println("[프로젝트 목록]");
            
            for (int i = 0; i < psize; i++) {
              // 번호, 프로젝트명, 시작일, 종료일, 만든이
              System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
                  pno[i], ptitle[i], pstartDate[i], pendDate[i], powner[i]);
            }
            break;
          case "/task/add":
            System.out.println("[작업 등록]");
            
            System.out.print("번호? ");
            tno[tsize] = Integer.parseInt(keyboardScan.nextLine());

            System.out.print("내용? ");
            tcontent[tsize] = keyboardScan.nextLine();

            System.out.print("마감일? ");
            tdeadline[tsize] = Date.valueOf(keyboardScan.nextLine());

            System.out.println("상태?");
            System.out.println("0: 신규");
            System.out.println("1: 진행중");
            System.out.println("2: 완료");
            System.out.print("> ");
            tstatus[tsize] = Integer.valueOf(keyboardScan.nextLine());

            System.out.print("담당자? ");
            towner[tsize] = keyboardScan.nextLine();

            tsize++;
            break;
          case "/task/list":
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

    keyboardScan.close();
  }
}
