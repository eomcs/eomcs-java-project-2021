package com.eomcs.pms.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.BoardService;

@Controller
@RequestMapping("/board/")
public class BoardController {

  BoardService boardService;

  public BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  @RequestMapping(path="add", method = RequestMethod.GET)
  public String form(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/jsp/board/form.jsp";
  }

  @RequestMapping(path="add", method = RequestMethod.POST)
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Board b = new Board();
    b.setTitle(request.getParameter("title"));
    b.setContent(request.getParameter("content"));

    // 작성자는 로그인 사용자이다.
    HttpServletRequest httpRequest = request;
    Member loginUser = (Member) httpRequest.getSession().getAttribute("loginUser");
    b.setWriter(loginUser);

    boardService.add(b);
    return "redirect:list";
  }

  @RequestMapping(path="delete", method=RequestMethod.GET)
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Board oldBoard = boardService.get(no);
    if (oldBoard == null) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    }

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (oldBoard.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("삭제 권한이 없습니다!");
    }

    boardService.delete(no);

    return "redirect:list";
  }

  @RequestMapping(path="detail", method=RequestMethod.GET)
  public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    Board board = boardService.get(no);
    request.setAttribute("board", board);
    return "/jsp/board/detail.jsp";
  }

  @RequestMapping(value="list", method=RequestMethod.GET)
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String keyword = request.getParameter("keyword");
    List<Board> boards = null;
    if (keyword != null && keyword.length() > 0) {
      boards = boardService.search(keyword);
    } else {
      boards = boardService.list();
    }

    request.setAttribute("list", boards);

    return "/jsp/board/list.jsp";
  }

  @RequestMapping(value="update", method=RequestMethod.POST)
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));

    Board oldBoard = boardService.get(no);
    if (oldBoard == null) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    } 

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (oldBoard.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("변경 권한이 없습니다!");
    }

    Board board = new Board();
    board.setNo(oldBoard.getNo());
    board.setTitle(request.getParameter("title"));
    board.setContent(request.getParameter("content"));
    boardService.update(board);

    return "redirect:list";
  }
}






