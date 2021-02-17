package com.eomcs.pms.handler;

import java.util.ArrayList;
import java.util.Iterator;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberHandler {

  private ArrayList<Member> memberList = new ArrayList<>();

  public void add() {
    System.out.println("[회원 등록]");

    Member m = new Member();

    m.setNo(Prompt.inputInt("번호? "));
    m.setName(Prompt.inputString("이름? "));
    m.setEmail(Prompt.inputString("이메일? "));
    m.setPassword(Prompt.inputString("암호? "));
    m.setPhoto(Prompt.inputString("사진? "));
    m.setTel(Prompt.inputString("전화? "));
    m.setRegisteredDate(new java.sql.Date(System.currentTimeMillis()));

    memberList.add(m);

    System.out.println("회원을 등록하였습니다.");
  }

  public void list() throws CloneNotSupportedException {
    System.out.println("[회원 목록]");

    Iterator<Member> iterator = memberList.iterator();

    while (iterator.hasNext()) {
      Member m = iterator.next();
      System.out.printf("%d, %s, %s, %s, %s\n",
          m.getNo(), m.getName(), m.getEmail(), m.getTel(), m.getRegisteredDate());
    }
  }

  public void detail() {
    System.out.println("[회원 상세보기]");

    int no = Prompt.inputInt("번호? ");

    Member member = findByNo(no);
    if (member == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    System.out.printf("이름: %s\n", member.getName());
    System.out.printf("이메일: %s\n", member.getEmail());
    System.out.printf("사진: %s\n", member.getPhoto());
    System.out.printf("전화: %s\n", member.getTel());
    System.out.printf("가입일: %s\n", member.getRegisteredDate());

  }

  public void update() {
    System.out.println("[회원 변경]");

    int no = Prompt.inputInt("번호? ");

    Member member = findByNo(no);
    if (member == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    String name = Prompt.inputString(String.format("이름(%s)? ", member.getName()));
    String email = Prompt.inputString(String.format("이메일(%s)? ", member.getEmail()));
    String photo = Prompt.inputString(String.format("사진(%s)? ", member.getPhoto()));
    String tel = Prompt.inputString(String.format("전화(%s)? ", member.getTel()));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      member.setName(name);
      member.setEmail(email);
      member.setPhoto(photo);
      member.setTel(tel);
      System.out.println("회원을 변경하였습니다.");

    } else {
      System.out.println("회원 변경을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[회원 삭제]");

    int no = Prompt.inputInt("번호? ");

    Member member = findByNo(no);
    if (member == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      memberList.remove(member);
      System.out.println("회원을 삭제하였습니다.");

    } else {
      System.out.println("회원 삭제를 취소하였습니다.");
    }

  }

  public String inputMember(String promptTitle) {
    while (true) {
      String name = Prompt.inputString(promptTitle);
      if (name.length() == 0) {
        return null;
      } 
      if (findByName(name) != null) {
        return name;
      }
      System.out.println("등록된 회원이 아닙니다.");
    }
  }

  public String inputMembers(String promptTitle) {
    String members = "";
    while (true) {
      String name = inputMember(promptTitle);
      if (name == null) {
        return members;
      } else {
        if (!members.isEmpty()) {
          members += ",";
        }
        members += name;
      }
    }
  }

  private Member findByNo(int boardNo) {
    Member[] list = memberList.toArray(new Member[memberList.size()]);
    for (Member m : list) {
      if (m.getNo() == boardNo) {
        return m;
      }
    }
    return null;
  }

  private Member findByName(String name) {
    Member[] list = memberList.toArray(new Member[memberList.size()]);
    for (Member m : list) {
      if (m.getName().equals(name)) {
        return m;
      }
    }
    return null;
  }
}






