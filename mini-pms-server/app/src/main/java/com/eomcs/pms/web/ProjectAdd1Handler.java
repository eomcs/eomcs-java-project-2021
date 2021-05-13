package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/project/add1")
public class ProjectAdd1Handler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>새 프로젝트</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>새 프로젝트: 1단계</h1>");    

    out.println("<form action='add2' method='post'>");
    out.println("제목: <input type='text' name='title'><br>");
    out.println("<p><input type='submit' value='다음'>");
    out.println("<a href='list'>취소</a></p>");
    out.println("</form>");

    out.println("</body>");
    out.println("</html>");
  }
}








