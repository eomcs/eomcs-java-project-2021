package com.eomcs.util;

import java.sql.Date;
import java.util.Scanner;

// 패키지 소속 클래스 = top level class
// - 공개(public) : 다른 패키지에서 사용할 수 있음.
// - 비공개 : 같은 패키지인 경우만 사용할 수 있음.
public class Prompt {

  static Scanner keyboardScan = new Scanner(System.in);

  // 메서드 접근 범위 조정 
  // - public : 다른 패키지에서 사용할 수 있음.
  // - (default): 같은 패키지에 소속된 경우에만 사용할 수 있음.
  // - protected: 같은 패키지 및 자손 클래스인 경우 사용할 수 있음.
  // - private: 클래스 안에서만 사용할 수 있음.
  public static String inputString(String title) {
    System.out.print(title);
    return keyboardScan.nextLine();
  }

  public static int inputInt(String title) {
    return Integer.parseInt(inputString(title));
  }

  public static Date inputDate(String title) {
    return Date.valueOf(inputString(title));
  }

  public static void close() {
    keyboardScan.close();
  }
}
