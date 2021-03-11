package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class MemberListHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[회원 목록]");

    // 서버에 데이터 목록을 달라고 요청한다.
    out.writeUTF("member/selectall");
    out.writeInt(0);
    out.flush();

    // 서버의 응답 데이터를 읽는다.
    String status = in.readUTF();
    int length = in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    for (int i = 0; i < length; i++) {
      String[] fields = in.readUTF().split(",");

      System.out.printf("%s, %s, %s, %s, %s\n",
          fields[0], 
          fields[1], 
          fields[2],
          fields[3],
          fields[4]);
    }
  }
}






