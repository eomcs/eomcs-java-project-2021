package com.eomcs.pms.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@SuppressWarnings("serial")
@WebServlet("/member/list") 
public class MemberListHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

    try { 
      List<Member> list = memberService.list(request.getParameter("keyword"));

      request.setAttribute("list", list);
      response.setContentType("text/html;charset=UTF-8");
      request.getRequestDispatcher("/jsp/member/list.jsp").include(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}






