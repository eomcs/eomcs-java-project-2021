package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.eomcs.util.Prompt;

public class BoardSearchHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    String keyword = Prompt.inputString("검색어? ");

    if (keyword.length() == 0) {
      System.out.println("검색어를 입력하세요.");
      return;
    }

    // 서버에 지정한 번호의 게시글을 요청한다.
    out.writeUTF("board/selectByKeyword");
    out.writeInt(1);
    out.writeUTF(keyword);
    out.flush();

    // 서버의 응답을 받는다.
    String status = in.readUTF();
    int length = in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    if (length == 0) {
      System.out.println("검색어에 해당하는 게시글이 없습니다.");
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






