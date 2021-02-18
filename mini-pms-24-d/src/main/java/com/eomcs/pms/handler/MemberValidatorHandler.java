package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberValidatorHandler extends AbstractMemberHandler {

  public MemberValidatorHandler(List<Member> memberList) {
    super(memberList);
  }

  @Override
  public void service() {
    // 이 클래스는 실제 사용자의 요청을 처리하지 않는다.
    // 다만 프로젝트와 작업 관련 코드에서 
    // 유효한 회원 이름을 입력 받을 때 사용하기 위해 만든 클래스이다.
    // 그러나 이 클래스도 AbstractMemberHandler를 상속 받았기 때문에
    // Command 규칙에 따라 메서드를 만들어야 한다.
    // 그냥 빈 채로 둔다.
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

}






