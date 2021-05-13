package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@SuppressWarnings("serial")
@WebServlet("/project/add")
public class ProjectAddHandler extends HttpServlet {

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
    out.println("<h1>새 프로젝트</h1>");
    out.println("<form action='add' method='post'>");
    out.println("제목: <input type='text' name='title'><br>");
    out.println("내용: <textarea name='content' rows='10' cols='60'></textarea><br>");
    out.println("시작일: <input type='date' name='startDate'><br>");
    out.println("종료일: <input type='date' name='endDate'><br>");
    out.println("팀원: <br>");

    // 팀원 출력은 다른 서블릿에게 맡긴다.
    request.getRequestDispatcher("/project/member/list").include(request, response);

    // 인클루딩은 이전에 출력한 내용을 그대로 유지한다.
    // 인클루딩 서블릿을 실행한 후, 리턴된 뒤에 수행하는 출력도 유효한다. 
    // 

    out.println("<p><input type='submit' value='등록'>");
    out.println("<a href='list'>목록</a></p>");

    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ProjectService projectService = (ProjectService) request.getServletContext().getAttribute("projectService");

    try {
      Project p = new Project();
      p.setTitle(request.getParameter("title"));
      p.setContent(request.getParameter("content"));
      p.setStartDate(Date.valueOf(request.getParameter("startDate")));
      p.setEndDate(Date.valueOf(request.getParameter("endDate")));

      Member loginUser = (Member) request.getSession().getAttribute("loginUser");
      p.setOwner(loginUser);

      // ...&member=1&member=18&member=23
      String[] values = request.getParameterValues("member");
      ArrayList<Member> memberList = new ArrayList<>();
      if (values != null) {
        for (String value : values) {
          Member member = new Member();
          member.setNo(Integer.parseInt(value));
          memberList.add(member);
        }
      }
      p.setMembers(memberList);

      projectService.add(p);

      response.sendRedirect("list");

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error").forward(request, response);
      return;
    }
  }
}








