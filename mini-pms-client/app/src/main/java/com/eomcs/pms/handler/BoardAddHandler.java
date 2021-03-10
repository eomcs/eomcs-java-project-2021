package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

public class BoardAddHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) {
    try {
      System.out.println("[게시글 등록]");

      Board b = new Board();

      b.setTitle(Prompt.inputString("제목? "));
      b.setContent(Prompt.inputString("내용? "));
      b.setWriter(Prompt.inputString("작성자? "));

      // 서버에 데이터 입력을 요청한다.
      out.writeUTF("board/insert");
      out.writeInt(1);
      out.writeUTF(String.format("%s,%s,%s", b.getTitle(), b.getContent(), b.getWriter()));
      out.flush();

      // 서버의 응답을 읽는다.
      String status = in.readUTF();
      in.readInt();

      if (status.equals("error")) {
        System.out.println(in.readUTF());
        return;
      }
      System.out.println("게시글을 등록하였습니다.");

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}






