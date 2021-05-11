package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
@WebServlet("/project/detail")
public class ProjectDetailHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ProjectService projectService = (ProjectService) request.getServletContext().getAttribute("projectService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>프로젝트 상세</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>프로젝트 상세보기</h1>");

    try {
      int no = Integer.parseInt(request.getParameter("no"));

      Project project = projectService.get(no);
      if (project == null) {
        out.println("<p>해당 번호의 프로젝트가 없습니다.</p>");
        out.println("</body>");
        out.println("</html>");
        return;
      }

      out.println("<form action='update' method='post'>");
      out.printf("번호: <input type='text' name='no' value='%d' readonly><br>\n", project.getNo());
      out.printf("제목: <input type='text' name='title' value='%s'><br>\n", project.getTitle());
      out.printf("내용: <textarea name='content' rows='10' cols='60'>%s</textarea><br>\n", project.getContent());
      out.printf("시작일: <input type='date' name='startDate' value='%s'><br>\n", project.getStartDate());
      out.printf("종료일: <input type='date' name='endDate' value='%s'><br>\n", project.getEndDate());
      out.printf("관리자: %s<br>\n", project.getOwner().getName());
      out.println("팀원: <br>");

      MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");
      List<Member> members = memberService.list(null);
      for (Member m : members) {
        out.printf("  <input type='checkbox' name='member' value='%d' %s>%s<br>\n", 
            m.getNo(), contain(project.getMembers(), m.getNo()) ? "checked" : "", m.getName());
      }

      out.println("<input type='submit' value='변경'> ");
      out.printf("<a href='delete?no=%d'>삭제</a>\n", project.getNo());
      out.println("</form>");

    } catch (Exception e) {
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);
      out.printf("<pre>%s</pre>\n", strWriter.toString());
    }
    out.println("<p><a href='list'>목록</a></p>");

    out.println("</body>");
    out.println("</html>");
  }

  private boolean contain(List<Member> members, int memberNo) {
    for (Member m : members) {
      if (m.getNo() == memberNo) {
        return true;
      }
    }
    return false;
  }
}








