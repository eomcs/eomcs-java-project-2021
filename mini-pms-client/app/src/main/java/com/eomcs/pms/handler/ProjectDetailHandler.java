package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.eomcs.util.Prompt;

public class ProjectDetailHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[프로젝트 상세보기]");

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

    System.out.printf("프로젝트명: %s\n", fields[1]);
    System.out.printf("내용: %s\n", fields[2]);
    System.out.printf("시작일: %s\n", fields[3]);
    System.out.printf("종료일: %s\n", fields[4]);
    System.out.printf("관리자: %s\n", fields[5]);
    System.out.printf("팀원: %s\n", fields[6]);

  }
}








