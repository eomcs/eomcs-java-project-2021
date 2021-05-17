package com.eomcs.pms.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@SuppressWarnings("serial")
@WebServlet("/project/add3")
public class ProjectAdd3Handler extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 클라이언트에서 보낸 값을 세션에 보관한다.
    HttpSession session = request.getSession();
    session.setAttribute("content", request.getParameter("content"));
    session.setAttribute("startDate", request.getParameter("startDate"));
    session.setAttribute("endDate", request.getParameter("endDate"));

    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");
    try {
      List<Member> members = memberService.list(null);
      request.setAttribute("members", members);

      response.setContentType("text/html;charset=UTF-8");
      request.getRequestDispatcher("/jsp/project/form3.jsp").include(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}








