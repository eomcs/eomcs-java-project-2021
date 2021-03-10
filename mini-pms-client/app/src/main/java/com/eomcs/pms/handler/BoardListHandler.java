package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class BoardListHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) {
    try {
      System.out.println("[게시글 목록]");

      // 서버에 게시글 목록을 달라고 요청한다.
      out.writeUTF("board/selectall");
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
    } catch (Exception e) {
      // RuntimeException 계열의 예외를 던질 때는 
      // 메서드 선언부에 지정할 필요가 없다.
      // 그래도 이 메서드를 호출하는 쪽에서는 예외 처리를 해야 한다.
      throw new RuntimeException(e);
    }
  }
}






