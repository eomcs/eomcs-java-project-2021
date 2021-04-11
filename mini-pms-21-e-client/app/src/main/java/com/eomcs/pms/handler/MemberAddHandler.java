package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberAddHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[회원 등록]");

    Member m = new Member();
    m.setName(Prompt.inputString("이름? "));
    m.setEmail(Prompt.inputString("이메일? "));
    m.setPassword(Prompt.inputString("암호? "));
    m.setPhoto(Prompt.inputString("사진? "));
    m.setTel(Prompt.inputString("전화? "));

    // 서버에 데이터 입력을 요청한다.
    out.writeUTF("member/insert");
    out.writeInt(1);
    out.writeUTF(String.format("%s,%s,%s,%s,%s", 
        m.getName(), m.getEmail(), m.getPassword(), m.getPhoto(), m.getTel()));
    out.flush();

    // 서버의 응답을 읽는다.
    String status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    System.out.println("회원을 등록하였습니다.");
  }
}






