package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Member;
import com.eomcs.util.List;
import com.eomcs.util.Prompt;

public class MemberHandler {

  private List memberList = new List();

  public List getMemberList() {
    return this.memberList;
  }

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

  public void list() {
    System.out.println("[회원 목록]");

    Object[] list = memberList.toArray();
    for (Object obj : list) {
      Member m = (Member) obj;
      // 번호, 이름, 이메일, 전화, 가입일
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
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

    int index = indexOf(no);
    if (index == -1) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      memberList.delete(index);
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

  private int indexOf(int memberNo) {
    Object[] list = memberList.toArray();
    for (int i = 0; i < list.length; i++) {
      Member m = (Member) list[i];
      if (m.getNo() == memberNo) {
        return i;
      }
    }
    return -1;
  }

  private Member findByNo(int boardNo) {
    Object[] list = memberList.toArray();
    for (Object obj : list) {
      Member m = (Member) obj;
      if (m.getNo() == boardNo) {
        return m;
      }
    }
    return null;
  }

  private Member findByName(String name) {
    Object[] list = memberList.toArray();
    for (Object obj : list) {
      Member m = (Member) obj;
      if (m.getName().equals(name)) {
        return m;
      }
    }
    return null;
  }
}






