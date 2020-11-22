package com.eomcs.lms;

import java.sql.Date;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {

    Scanner keyboard = new Scanner(System.in);
    
    // 강의 정보를 담을 메모리의 설계도를 만든다.
    // 클래스?
    // - 애플리케이션에서 다룰 특정 데이터(수업정보, 학생정보, 게시물, 제품정보 등)의 
    //   메모리 구조를 설계하는 문법이다.
    // - 이렇게 개발자가 새롭게 정의한 데이터 타입을 
    //   "사용자 정의 데이터 타입"이라 부른다.
    // - 즉 '클래스'는 '사용자 정의 데이터 타입'을 만들 때 사용하는 문법이다.
    //
    class Lesson {
      int no;
      String title;
      String description;
      Date startDate;
      Date endDate;
      int totalHours;
      int dayHours;
    }
    
    final int SIZE = 100;

    // Lesson 인스턴스 주소를 담을 레퍼런스 배열을 만든다.
    Lesson[] lessons = new Lesson[SIZE];
    
    int count = 0;
    
    for (int i = 0; i < SIZE; i++) {
      count++;

      Lesson lesson = new Lesson();
      
      System.out.print("번호? ");
      lesson.no = keyboard.nextInt();

      keyboard.nextLine(); // nextInt() 후에 남아 있는 줄바꿈 기호를 제거한다.

      System.out.print("수업명? ");
      lesson.title = keyboard.nextLine();

      System.out.print("설명? ");
      lesson.description = keyboard.nextLine();

      System.out.print("시작일? ");
      // "yyyy-MM-dd" 형태로 입력된 문자열을 날짜 정보로 바꾼다.
      lesson.startDate = Date.valueOf(keyboard.next());

      System.out.print("종료일? ");
      lesson.endDate = Date.valueOf(keyboard.next());

      System.out.print("총수업시간? ");
      lesson.totalHours = keyboard.nextInt();

      System.out.print("일수업시간? ");
      lesson.dayHours = keyboard.nextInt();
      keyboard.nextLine(); // 일수업시간 입력 값 다음에 남아 있는 줄바꿈 값 제거
      
      // 수업 정보를 담고 있는 인스턴스의 주소를 나중에 사용할 수 있도록 
      // 레퍼런스 배열에 보관해 둔다.
      lessons[i] = lesson;
      
      System.out.print("계속 입력하시겠습니까?(Y/n) ");
      String response = keyboard.nextLine();
      if (!response.equalsIgnoreCase("y"))
        break;
    }
    System.out.println();

    for (int i = 0; i < count; i++) {
      Lesson lesson = lessons[i];
      System.out.printf("%d, %s, %s ~ %s, %d\n",
          lesson.no, lesson.title, 
          lesson.startDate, lesson.endDate, 
          lesson.totalHours);
    }

    keyboard.close();
  }
}






