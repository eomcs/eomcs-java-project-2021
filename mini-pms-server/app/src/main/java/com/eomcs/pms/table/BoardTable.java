package com.eomcs.pms.table;

import java.io.File;
import java.sql.Date;
import java.util.List;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.JsonFileHandler;
import com.eomcs.util.Request;
import com.eomcs.util.Response;

// 1) 간단한 동작 테스트를 위해 임의의 값을 리턴한다.
// 2) JSON 포맷의 파일을 로딩한다.
public class BoardTable implements DataTable {

  File jsonFile = new File("boards.json");
  List<Board> list;

  public BoardTable() {
    list = JsonFileHandler.loadObjects(jsonFile, Board.class);
  }

  @Override
  public void service(Request request, Response response) throws Exception {
    Board board = null;
    String[] fields = null;

    switch (request.getCommand()) {
      case "board/insert":

        // data에서 CSV 형식으로 표현된 문자열을 꺼내 각 필드 값으로 분리한다.
        fields = request.getData().get(0).split(",");

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

        // 게시글을 목록에 추가하는 즉시 List 컬렉션의 전체 데이터를 파일에 저장한다.
        // - 매번 전체 데이터를 파일에 저장하는 것은 비효율적이다.
        // - 효율성에 대한 부분은 무시한다.
        // - 현재 중요한 것은 서버 애플리케이션에서 데이터 파일을 관리한다는 점이다.
        // - 어차피 이 애플리케이션은 진정한 성능을 제공하는 DBMS으로 교체할 것이다.
        // 
        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      case "board/selectall":
        for (Board b : list) {
          response.appendData(String.format("%d,%s,%s,%s,%d", 
              b.getNo(), b.getTitle(), b.getWriter(), b.getRegisteredDate(), b.getViewCount()));
        }
        break;
      case "board/select":
        int no = Integer.parseInt(request.getData().get(0));

        board = getBoard(no);
        if (board != null) {
          response.appendData(String.format("%d,%s,%s,%s,%s,%d", 
              board.getNo(), 
              board.getTitle(), 
              board.getContent(),
              board.getWriter(), 
              board.getRegisteredDate(), 
              board.getViewCount()));
        } else {
          throw new Exception("해당 번호의 게시글이 없습니다.");
        }
        break;
      case "board/selectByKeyword":
        String keyword = request.getData().get(0);

        // 전체 게시글을 가져와서 검색어를 포함하는 게시글을 찾는다.
        // 찾은 게시글을 CSV 문자열로 만들어 응답할 데이터에 추가한다.
        for (Board b : list) {
          if (b.getTitle().contains(keyword) || 
              b.getContent().contains(keyword) ||
              b.getWriter().contains(keyword)) {

            response.appendData(String.format("%d,%s,%s,%s,%d", 
                b.getNo(), 
                b.getTitle(), 
                b.getWriter(), 
                b.getRegisteredDate(), 
                b.getViewCount()));
          }
        }
        break;
      case "board/update":
        fields = request.getData().get(0).split(",");

        board = getBoard(Integer.parseInt(fields[0]));
        if (board == null) {
          throw new Exception("해당 번호의 게시글이 없습니다.");
        }

        // 해당 게시물의 제목과 내용을 변경한다.
        // - List 에 보관된 객체를 꺼낸 것이기 때문에 
        //   그냥 그 객체의 값을 변경하면 된다.
        board.setTitle(fields[1]);
        board.setContent(fields[2]);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      case "board/delete":
        no = Integer.parseInt(request.getData().get(0));
        board = getBoard(no);
        if (board == null) {
          throw new Exception("해당 번호의 게시글이 없습니다.");
        }

        list.remove(board);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      default:
        throw new Exception("해당 명령을 처리할 수 없습니다.");
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
