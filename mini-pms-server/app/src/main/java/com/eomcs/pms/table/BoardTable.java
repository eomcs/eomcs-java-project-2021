package com.eomcs.pms.table;

import java.io.DataOutputStream;
import java.util.List;

public class BoardTable implements DataTable {
  @Override
  public void service(String command, List<String> data, DataOutputStream out) throws Exception {
    switch (command) {
      case "board/insert":
        out.writeUTF("success");
        out.writeInt(1);
        out.writeUTF("입력 성공!");
        break;
      case "board/selectall":
        out.writeUTF("success");
        out.writeInt(3);
        out.writeUTF("목록 성공!");
        out.writeUTF("목록 성공!");
        out.writeUTF("목록 성공!");
        break;
      case "board/select":

        break;
      case "board/update":
        break;
      case "board/delete":
        break;
      default:

    }
  }
}
