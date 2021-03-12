package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.driver.Statement;
import com.eomcs.util.Prompt;

public class ProjectUpdateHandler implements Command {

  @Override
  public void service(Statement stmt) throws Exception {
    System.out.println("[프로젝트 변경]");

    int no = Prompt.inputInt("번호? ");

    String[] fields = stmt.executeQuery("project/select", Integer.toString(no)).next().split(",");

    String title = Prompt.inputString(String.format("프로젝트명(%s)? ", fields[1]));
    String content = Prompt.inputString(String.format("내용(%s)? ", fields[2]));
    Date startDate = Prompt.inputDate(String.format("시작일(%s)? ", fields[3]));
    Date endDate = Prompt.inputDate(String.format("종료일(%s)? ", fields[4]));

    String owner = MemberValidator.inputMember(String.format("만든이(%s)?(취소: 빈 문자열) ", fields[5]), stmt);
    if (owner == null) {
      System.out.println("프로젝트 변경을 취소합니다.");
      return;
    }

    String members = MemberValidator.inputMembers(
        String.format("팀원(%s)?(완료: 빈 문자열) ", fields[6]), stmt);

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("프로젝트 변경을 취소하였습니다.");
      return;
    }

    stmt.executeUpdate("project/update", 
        String.format("%d,%s,%s,%s,%s,%s,%s", 
            no, title, content, startDate, endDate, owner, members));

    System.out.println("프로젝트을 변경하였습니다.");
  }

}








