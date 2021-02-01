package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberHandler {

  public MemberList memberList = new MemberList();

  public void add() {
    System.out.println("[회원 등록]");

    Member m = new Member();

    m.no = Prompt.inputInt("번호? ");
    m.name = Prompt.inputString("이름? ");
    m.email = Prompt.inputString("이메일? ");
    m.password = Prompt.inputString("암호? ");
    m.photo = Prompt.inputString("사진? ");
    m.tel = Prompt.inputString("전화? ");
    m.registeredDate = new java.sql.Date(System.currentTimeMillis());

    memberList.add(m);

    System.out.println("회원을 등록하였습니다.");
  }

  public void list() {
    System.out.println("[회원 목록]");

    Member[] members = memberList.toArray();
    for (Member m : members) {
      // 번호, 이름, 이메일, 전화, 가입일
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          m.no, m.name, m.email, m.tel, m.registeredDate);
    }
  }

  public void detail() {
    System.out.println("[회원 상세보기]");

    int no = Prompt.inputInt("번호? ");

    Member member = memberList.get(no);
    if (member == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    System.out.printf("이름: %s\n", member.name);
    System.out.printf("이메일: %s\n", member.email);
    System.out.printf("사진: %s\n", member.photo);
    System.out.printf("전화: %s\n", member.tel);
    System.out.printf("가입일: %s\n", member.registeredDate);

  }

  public void update() {
    System.out.println("[회원 변경]");

    int no = Prompt.inputInt("번호? ");

    Member member = memberList.get(no);
    if (member == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    System.out.printf("이름: %s\n", member.name);
    System.out.printf("이메일: %s\n", member.email);
    System.out.printf("사진: %s\n", member.photo);
    System.out.printf("전화: %s\n", member.tel);
    System.out.printf("가입일: %s\n", member.registeredDate);

    String name = Prompt.inputString(String.format("이름(%s)? ", member.name));
    String email = Prompt.inputString(String.format("이메일(%s)? ", member.email));
    String photo = Prompt.inputString(String.format("사진(%s)? ", member.photo));
    String tel = Prompt.inputString(String.format("전화(%s)? ", member.tel));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      member.name = name;
      member.email = email;
      member.photo = photo;
      member.tel = tel;
      System.out.println("회원을 변경하였습니다.");

    } else {
      System.out.println("회원 변경을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[회원 삭제]");

    int no = Prompt.inputInt("번호? ");

    Member member = memberList.get(no);
    if (member == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      memberList.delete(no);
      System.out.println("회원을 삭제하였습니다.");

    } else {
      System.out.println("회원 삭제를 취소하였습니다.");
    }

  }
}






