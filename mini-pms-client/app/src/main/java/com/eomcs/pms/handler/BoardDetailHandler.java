package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.eomcs.util.Prompt;

public class BoardDetailHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) {
    try {
      System.out.println("[게시글 상세보기]");

      int no = Prompt.inputInt("번호? ");

      // 서버에 지정한 번호의 게시글을 요청한다.
      out.writeUTF("board/select");
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

      System.out.printf("제목: %s\n", fields[1]);
      System.out.printf("내용: %s\n", fields[2]);
      System.out.printf("작성자: %s\n", fields[3]);
      System.out.printf("등록일: %s\n", fields[4]);
      System.out.printf("조회수: %s\n", fields[5]);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}






