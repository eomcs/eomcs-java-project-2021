package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.util.Component;
import com.eomcs.util.PageController;

@Component("/member/detail")
public class MemberDetailHandler implements PageController {

  MemberService memberService;

  public MemberDetailHandler(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));

    Member m = memberService.get(no);
    request.setAttribute("member", m);
    return "/jsp/member/detail.jsp";
  }
}






