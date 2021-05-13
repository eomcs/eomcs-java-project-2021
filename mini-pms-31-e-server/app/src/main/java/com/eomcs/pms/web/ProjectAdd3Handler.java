package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>새 프로젝트</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>새 프로젝트: 3단계</h1>");

    out.println("<form action='add' method='post'>");
    out.println("팀원: <br>");
    request.getRequestDispatcher("/project/member/list").include(request, response);
    out.println("<p><input type='submit' value='등록'>");
    out.println("<a href='list'>취소</a></p>");
    out.println("</form>");

    out.println("</body>");
    out.println("</html>");
  }
}








