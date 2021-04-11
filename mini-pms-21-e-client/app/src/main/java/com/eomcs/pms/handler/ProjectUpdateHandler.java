package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.sql.Date;
import com.eomcs.util.Prompt;

public class ProjectUpdateHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[프로젝트 변경]");

    int no = Prompt.inputInt("번호? ");

    // 서버에 지정한 번호의 데이터를 요청한다.
    out.writeUTF("project/select");
    out.writeInt(1);
    out.writeUTF(Integer.toString(no));
    out.flush();

    // 서버의 응답을 받는다.
    String status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    String[] fields = in.readUTF().split(",");

    String title = Prompt.inputString(String.format("프로젝트명(%s)? ", fields[1]));
    String content = Prompt.inputString(String.format("내용(%s)? ", fields[2]));
    Date startDate = Prompt.inputDate(String.format("시작일(%s)? ", fields[3]));
    Date endDate = Prompt.inputDate(String.format("종료일(%s)? ", fields[4]));

    String owner = MemberValidator.inputMember(String.format("만든이(%s)?(취소: 빈 문자열) ", fields[5]), in, out);
    if (owner == null) {
      System.out.println("프로젝트 변경을 취소합니다.");
      return;
    }

    String members = MemberValidator.inputMembers(
        String.format("팀원(%s)?(완료: 빈 문자열) ", fields[6]), in, out);

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("프로젝트 변경을 취소하였습니다.");
      return;
    }

    // 서버에 데이터 변경을 요청한다.
    out.writeUTF("project/update");
    out.writeInt(1);
    out.writeUTF(String.format("%d,%s,%s,%s,%s,%s,%s", 
        no, title, content, startDate, endDate, owner, members));
    out.flush();

    // 서버의 응답을 받는다.
    status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    System.out.println("프로젝트을 변경하였습니다.");
  }

}








