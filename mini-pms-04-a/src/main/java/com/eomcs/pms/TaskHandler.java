package com.eomcs.pms;

import java.sql.Date;

public class TaskHandler {

  static final int LENGTH = 100;
  static int[] no = new int[LENGTH];
  static String[] content = new String[LENGTH];
  static Date[] deadline = new Date[LENGTH];
  static String[] owner = new String[LENGTH];
  static int[] status = new int[LENGTH];
  static int size = 0;

  static void add() {
    System.out.println("[작업 등록]");

    no[size] = Prompt.inputInt("번호? ");
    content[size] = Prompt.inputString("내용? ");
    deadline[size] = Prompt.inputDate("마감일? ");
    status[size] = Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> ");
    owner[size] = Prompt.inputString("담당자? ");

    size++;
  }

  static void list() {
    System.out.println("[작업 목록]");

    for (int i = 0; i < size; i++) {
      String stateLabel = null;
      switch (status[i]) {
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
          no[i], content[i], deadline[i], stateLabel, owner[i]);
    }
  }
}
