package com.eomcs.lms;

import java.sql.Date;
import java.util.Scanner;

// 입력을 카운트하기
public class App3 {
  public static void main(String[] args) {
    Scanner keyboard = new Scanner(System.in);

    int[] no = new int[100];
    String[] title = new String[100];
    Date[] date = new Date[100];
    int[] viewCount = new int[100];
    String response;

    int count = 0;
    for (int i = 0; i < 100; i++) {
      System.out.print("번호? ");
      no[i] = keyboard.nextInt();
      keyboard.nextLine(); // 줄바꿈 기호 제거용

      System.out.print("내용? ");
      title[i] = keyboard.nextLine();

      date[i] = new Date(System.currentTimeMillis());
      viewCount[i] = 0;
      
      count++;
      
      System.out.println();

      System.out.print("계속 입력하시겠습니까?(Y/n) ");
      response = keyboard.nextLine();
      if (!response.equalsIgnoreCase("y")) {
        break;
      }
    }

    keyboard.close();

    System.out.println();

    for (int i = 0; i < count; i++) {
      System.out.printf("%d, %s, %s, %d\n", 
          no[i], title[i], date[i], viewCount[i]);
    }
  }
}








