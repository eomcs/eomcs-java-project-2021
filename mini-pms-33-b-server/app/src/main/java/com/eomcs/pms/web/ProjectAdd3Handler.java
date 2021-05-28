package com.eomcs.pms.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.util.Component;
import com.eomcs.util.PageController;

@Component("/project/add3")
public class ProjectAdd3Handler implements PageController {

  MemberService memberService;

  public ProjectAdd3Handler(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession();
    session.setAttribute("content", request.getParameter("content"));
    session.setAttribute("startDate", request.getParameter("startDate"));
    session.setAttribute("endDate", request.getParameter("endDate"));

    List<Member> members = memberService.list(null);
    request.setAttribute("members", members);
    return "/jsp/project/form3.jsp";
  }
}








