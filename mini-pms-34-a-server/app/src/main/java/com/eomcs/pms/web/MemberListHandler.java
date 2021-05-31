package com.eomcs.pms.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@Controller
public class MemberListHandler {

  MemberService memberService;

  public MemberListHandler(MemberService memberService) {
    this.memberService = memberService;
  }

  @RequestMapping("/member/list") 
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    List<Member> list = memberService.list(request.getParameter("keyword"));
    request.setAttribute("list", list);
    return "/jsp/member/list.jsp";
  }
}






