package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@SuppressWarnings("serial")
@WebServlet("/member/check")
public class MemberCheckHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

    try {
      String email = request.getParameter("email");

      Member m = memberService.get(email);

      response.setContentType("text/plain;charset=UTF-8");
      PrintWriter out = response.getWriter();

      if (m == null) {
        out.print("no");
      } else {
        out.print("yes");
      }


    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}






