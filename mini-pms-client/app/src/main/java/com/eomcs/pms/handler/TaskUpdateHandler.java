package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.sql.Date;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskUpdateHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[작업 변경]");

    int no = Prompt.inputInt("번호? ");

    // 서버에 지정한 번호의 데이터를 요청한다.
    out.writeUTF("task/select");
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

    String content = Prompt.inputString(String.format("내용(%s)? ", fields[1]));
    Date deadline = Prompt.inputDate(String.format("마감일(%s)? ", fields[2]));
    int stat = Prompt.inputInt(String.format(
        "상태(%s)?\n0: 신규\n1: 진행중\n2: 완료\n> ", 
        Task.getStatusLabel(Integer.parseInt(fields[3]))));
    String owner = MemberValidator.inputMember(String.format("담당자(%s)?(취소: 빈 문자열) ", fields[4]), in, out);
    if(owner == null) {
      System.out.println("작업 변경을 취소합니다.");
      return;
    }

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("작업 변경을 취소하였습니다.");
      return;
    }

    // 서버에 데이터 변경을 요청한다.
    out.writeUTF("task/update");
    out.writeInt(1);
    out.writeUTF(String.format("%s,%s,%s,%s,%s", no, content, deadline, stat, owner));
    out.flush();

    // 서버의 응답을 받는다.
    status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }
    System.out.println("작업을 변경하였습니다.");

  }

}
