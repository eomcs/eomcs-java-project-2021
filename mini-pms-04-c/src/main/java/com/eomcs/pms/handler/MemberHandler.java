package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.util.Prompt;

public class MemberHandler {

  // 회원 정보를 저장할 메모리의 설계도
  // - 각 항목의 데이터를 저장할 변수를 선언한다.
  // - 이 변수를 "필드(field)"라고 부른다.
  //
  static class Member {
    int no;
    String name;
    String email;
    String password;
    String photo;
    String tel;
    Date registeredDate;  
  }

  static final int LENGTH = 100;
  static Member[] members = new Member[LENGTH];  // 레퍼런스 배열 준비  
  static int size = 0;

  public static void add() {
    System.out.println("[회원 등록]");

    // 1) 회원 정보를 담을 메모리를 준비한다.
    Member m = new Member();

    // 2) 사용자가 입력한 값을 Member 인스턴스에 저장한다.
    m.no = Prompt.inputInt("번호? ");
    m.name = Prompt.inputString("이름? ");
    m.email = Prompt.inputString("이메일? ");
    m.password = Prompt.inputString("암호? ");
    m.photo = Prompt.inputString("사진? ");
    m.tel = Prompt.inputString("전화? ");
    m.registeredDate = new java.sql.Date(System.currentTimeMillis());

    // 3) 사용자의 정보가 저장된 인스턴스 주소를 레퍼런스 배열에 보관한다.
    members[size++] = m;
    // 위 문장은 컴파일할 때 다음 문장으로 변경된다.
    //    int temp = size;
    //    size++;
    //    members[temp] = m;

  }

  public static void list() {
    System.out.println("[회원 목록]");

    for (int i = 0; i < size; i++) {
      Member m = members[i];
      // 번호, 이름, 이메일, 전화, 가입일
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          m.no, m.name, m.email, m.tel, m.registeredDate);
    }
  }
}
