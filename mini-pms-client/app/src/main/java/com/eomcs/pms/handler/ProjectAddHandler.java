package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectAddHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[프로젝트 등록]");

    Project p = new Project();
    p.setTitle(Prompt.inputString("프로젝트명? "));
    p.setContent(Prompt.inputString("내용? "));
    p.setStartDate(Prompt.inputDate("시작일? "));
    p.setEndDate(Prompt.inputDate("종료일? "));

    p.setOwner(MemberValidator.inputMember("만든이?(취소: 빈 문자열) ", in, out));
    if (p.getOwner() == null) {
      System.out.println("프로젝트 입력을 취소합니다.");
      return;
    }

    p.setMembers(MemberValidator.inputMembers("팀원?(완료: 빈 문자열) ", in, out));

    // 서버에 데이터 입력을 요청한다.
    out.writeUTF("project/insert");
    out.writeInt(1);
    out.writeUTF(String.format("%s,%s,%s,%s,%s,%s", 
        p.getTitle(),
        p.getContent(),
        p.getStartDate(),
        p.getEndDate(),
        p.getOwner(),
        p.getMembers()));
    out.flush();

    // 서버의 응답을 읽는다.
    String status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    System.out.println("프로젝트를 등록했습니다.");
  }
}








