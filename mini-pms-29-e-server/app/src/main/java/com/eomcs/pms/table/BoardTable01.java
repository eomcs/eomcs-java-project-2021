package com.eomcs.pms.table;

import java.io.DataOutputStream;
import java.util.List;

// 1) 간단한 동작 테스트를 위해 임의의 값을 리턴한다.
public class BoardTable01 implements DataTable {
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
        out.writeUTF("목록!");
        out.writeUTF("목록!");
        out.writeUTF("목록!");
        break;
      case "board/select":
        out.writeUTF("success");
        out.writeInt(1);
        out.writeUTF("상세 정보!");
        break;
      case "board/update":
        out.writeUTF("success");
        out.writeInt(0);
        break;
      case "board/delete":
        out.writeUTF("success");
        out.writeInt(0);
        break;
      default:
        out.writeUTF("error");
        out.writeInt(1);
        out.writeUTF("해당 명령을 처리할 수 없습니다.");
    }
  }
}
