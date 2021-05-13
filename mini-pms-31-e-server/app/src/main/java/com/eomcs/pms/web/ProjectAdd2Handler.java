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
@WebServlet("/project/add2")
public class ProjectAdd2Handler extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 클라이언트에서 보낸 값을 세션에 보관한다.
    HttpSession session = request.getSession();
    session.setAttribute("title", request.getParameter("title"));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>새 프로젝트</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>새 프로젝트: 2단계</h1>");

    out.println("<form action='add3' method='post'>");
    out.println("내용: <textarea name='content' rows='10' cols='60'></textarea><br>");
    out.println("시작일: <input type='date' name='startDate'><br>");
    out.println("종료일: <input type='date' name='endDate'><br>");
    out.println("<p><input type='submit' value='다음'>");
    out.println("<a href='list'>취소</a></p>");
    out.println("</form>");

    out.println("</body>");
    out.println("</html>");
  }
}








