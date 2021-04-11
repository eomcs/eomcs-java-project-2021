package com.eomcs.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.eomcs.pms.domain.Task;

public class TaskListHandler implements Command {

  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[작업 목록]");

    // 서버에 데이터 목록을 달라고 요청한다.
    out.writeUTF("task/selectall");
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
          Task.getStatusLabel(Integer.parseInt(fields[3])),
          fields[4]);
    }
  }

}
