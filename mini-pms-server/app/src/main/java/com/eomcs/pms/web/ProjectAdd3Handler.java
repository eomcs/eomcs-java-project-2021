package com.eomcs.pms.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@Controller
public class ProjectAdd3Handler {

  MemberService memberService;

  public ProjectAdd3Handler(MemberService memberService) {
    this.memberService = memberService;
  }

  @RequestMapping("/project/add3")
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








