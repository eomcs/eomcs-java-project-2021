package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.eomcs.util.Prompt;

public class BoardUpdateHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) {
    try {
      System.out.println("[게시글 변경]");

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

      String title = Prompt.inputString(String.format("제목(%s)? ", fields[1]));
      String content = Prompt.inputString(String.format("내용(%s)? ", fields[2]));

      String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("게시글 변경을 취소하였습니다.");
        return;
      }

      // 서버에 데이터 변경을 요청한다.
      out.writeUTF("board/update");
      out.writeInt(1);
      out.writeUTF(String.format("%d,%s,%s", no, title, content));
      out.flush();

      // 서버의 응답을 받는다.
      status = in.readUTF();
      in.readInt();

      if (status.equals("error")) {
        System.out.println(in.readUTF());
        return;
      }

      System.out.println("게시글을 변경하였습니다.");

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}






