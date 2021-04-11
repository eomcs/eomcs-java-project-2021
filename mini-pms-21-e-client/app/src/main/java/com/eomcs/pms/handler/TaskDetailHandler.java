package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskDetailHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[작업 상세보기]");

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

    System.out.printf("내용: %s\n", fields[1]);
    System.out.printf("마감일: %s\n", fields[2]);
    System.out.printf("상태: %s\n", Task.getStatusLabel(Integer.parseInt(fields[3])));
    System.out.printf("담당자: %s\n", fields[4]);

  }

}
