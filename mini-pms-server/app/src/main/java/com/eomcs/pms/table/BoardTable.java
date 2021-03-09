package com.eomcs.pms.table;

import java.io.DataOutputStream;
import java.io.File;
import java.sql.Date;
import java.util.List;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.JsonFileHandler;

// 1) 간단한 동작 테스트를 위해 임의의 값을 리턴한다.
// 2) JSON 포맷의 파일을 로딩한다.
public class BoardTable implements DataTable {

  File jsonFile = new File("boards.json");
  List<Board> list;

  public BoardTable() {
    list = JsonFileHandler.loadObjects(jsonFile, Board.class);
  }

  @Override
  public void service(String command, List<String> data, DataOutputStream out) throws Exception {
    Board board = null;

    switch (command) {
      case "board/insert":

        // data에서 CSV 형식으로 표현된 문자열을 꺼내 각 필드 값으로 분리한다.
        String[] fields = data.get(0).split(",");

        // CSV 데이터를 Board 객체에 저장한다.
        board = new Board();

        // 새 게시글의 번호
        if (list.size() > 0) {
          board.setNo(list.get(list.size() - 1).getNo() + 1);
        } else {
          board.setNo(1);
        }

        board.setTitle(fields[0]);
        board.setContent(fields[1]);
        board.setWriter(fields[2]);
        board.setRegisteredDate(new Date(System.currentTimeMillis()));

        // 새 게시글을 담은 Board 객체를 목록에 저장한다.
        list.add(board);

        // 클라이언트에게 작업 성공을 알린다.
        out.writeUTF("success");
        out.writeInt(0);
        break;
      case "board/selectall":
        out.writeUTF("success");
        out.writeInt(list.size());

        for (Board b : list) {
          out.writeUTF(String.format("%d,%s,%s,%s,%d", 
              b.getNo(), b.getTitle(), b.getWriter(), b.getRegisteredDate(), b.getViewCount()));
        }
        break;
      case "board/select":
        int no = Integer.parseInt(data.get(0));

        board = getBoard(no);
        if (board != null) {
          out.writeUTF("success");
          out.writeInt(1);
          out.writeUTF(String.format("%d,%s,%s,%s,%s,%d", 
              board.getNo(), 
              board.getTitle(), 
              board.getContent(),
              board.getWriter(), 
              board.getRegisteredDate(), 
              board.getViewCount()));
        } else {
          out.writeUTF("error");
          out.writeInt(1);
          out.writeUTF("해당 번호의 게시글이 없습니다.");
        }
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

  private Board getBoard(int boardNo) {
    for (Board b : list) {
      if (b.getNo() == boardNo) {
        return b;
      }
    }
    return null;
  }
}
