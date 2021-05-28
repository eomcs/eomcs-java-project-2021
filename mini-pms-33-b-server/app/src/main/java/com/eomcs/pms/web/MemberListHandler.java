package com.eomcs.pms.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.util.Component;
import com.eomcs.util.PageController;

@Component("/member/list") 
public class MemberListHandler implements PageController {

  MemberService memberService;

  public MemberListHandler(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    List<Member> list = memberService.list(request.getParameter("keyword"));
    request.setAttribute("list", list);
    return "/jsp/member/list.jsp";
  }
}






