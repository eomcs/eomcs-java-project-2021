package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;

@SuppressWarnings("serial")
@WebServlet("/project/add")
public class ProjectAddHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

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
    try {
      List<Member> members = memberService.list(null);
      for (Member m : members) {
        out.printf("  <input type='checkbox' name='member' value='%d'>%s<br>\n", m.getNo(), m.getName());
      }
    } catch (Exception e) {
      throw new ServletException(e);
    }
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

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>프로젝트 등록</title>");

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

      out.println("<meta http-equiv='Refresh' content='1;url=list'>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>프로젝트 등록</h1>");
      out.println("<p>프로젝트를 등록했습니다.</p>");

    } catch (Exception e) {
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);

      out.println("</head>");
      out.println("<body>");
      out.println("<h1>프로젝트 등록 오류</h1>");
      out.printf("<pre>%s</pre>\n", strWriter.toString());
      out.println("<p><a href='list'>목록</a></p>");
    }

    out.println("</body>");
    out.println("</html>");
  }
}








