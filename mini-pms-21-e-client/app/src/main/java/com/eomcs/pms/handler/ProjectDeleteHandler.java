package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.eomcs.util.Prompt;

public class ProjectDeleteHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[프로젝트 삭제]");

    int no = Prompt.inputInt("번호? ");

    // 서버에 해당 번호의 게시글이 있는지 조회한다.
    out.writeUTF("project/select");
    out.writeInt(1);
    out.writeUTF(Integer.toString(no));
    out.flush();

    // 서버의 응답을 읽는다.
    String status = in.readUTF();
    in.readInt();
    String data = in.readUTF();

    if (status.equals("error")) {
      System.out.println(data);
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("프로젝트 삭제를 취소하였습니다.");
      return;
    }

    // 서버에 데이터 삭제를 요청한다.
    out.writeUTF("project/delete");
    out.writeInt(1);
    out.writeUTF(Integer.toString(no));
    out.flush();

    // 서버의 응답을 읽는다.
    status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    System.out.println("프로젝트 삭제를 취소하였습니다.");

  }
}








