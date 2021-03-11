package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.eomcs.util.Prompt;

public class MemberDetailHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[회원 상세보기]");

    int no = Prompt.inputInt("번호? ");

    // 서버에 지정한 번호의 데이터를 요청한다.
    out.writeUTF("member/select");
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

    System.out.printf("이름: %s\n", fields[1]);
    System.out.printf("이메일: %s\n", fields[2]);
    System.out.printf("사진: %s\n", fields[3]);
    System.out.printf("전화: %s\n", fields[4]);
    System.out.printf("가입일: %s\n", fields[5]);
  }
}






