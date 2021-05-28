package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.util.Component;
import com.eomcs.util.PageController;

@Component("/member/delete")
public class MemberDeleteHandler implements PageController {

  MemberService memberService;

  public MemberDeleteHandler(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Member member = memberService.get(no);
    if (member == null) {
      throw new Exception("해당 번호의 회원이 없습니다.");
    }

    // 회원 관리를 관리자가 할 경우 모든 회원의 정보 변경 가능
    //      Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    //      if (oldBoard.getWriter().getNo() != loginUser.getNo()) {
    //        throw new Exception("삭제 권한이 없습니다!");
    //      }

    memberService.delete(no);
    return "redirect:list";
  }
}






