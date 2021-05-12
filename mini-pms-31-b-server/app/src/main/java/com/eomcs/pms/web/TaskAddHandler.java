package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;

@SuppressWarnings("serial")
@WebServlet("/task/add")
public class TaskAddHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ProjectService projectService = (ProjectService) request.getServletContext().getAttribute("projectService");
    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>새 작업</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>새 작업</h1>");

    try {
      out.println("<form action='add' method='post'>");

      out.println("프로젝트: <select name='projectNo'>");
      List<Project> projects = projectService.list();
      for (Project project : projects) {
        out.printf("  <option value='%d'>%s</option>\n", project.getNo(), project.getTitle());
      }
      out.println("</select><br>");

      out.println("작업: <input type='text' name='content'><br>");
      out.println("마감일: <input type='date' name='deadline'><br>");

      out.println("담당자: <select name='owner'>");
      List<Member> members = memberService.list(null);
      for (Member m : members) {
        out.printf("  <option value='%d'>%s</option>\n", m.getNo(), m.getName());
      }
      out.println("</select><br>");

      out.println("상태: ");
      out.println("<input type='radio' name='status' value='0' checked>신규 ");
      out.println("<input type='radio' name='status' value='1'>진행중 ");
      out.println("<input type='radio' name='status' value='2'>완료 ");

      out.println("<input type='submit' value='등록'>");
      out.println("</form>");

    } catch (Exception e) {
      throw new ServletException(e);
    }
    out.println("</body>");
    out.println("</html>");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    TaskService taskService = (TaskService) request.getServletContext().getAttribute("taskService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>작업 등록</title>");

    try {
      Task t = new Task();
      t.setProjectNo(Integer.parseInt(request.getParameter("projectNo")));
      t.setContent(request.getParameter("content"));
      t.setDeadline(Date.valueOf(request.getParameter("deadline")));
      t.setStatus(Integer.parseInt(request.getParameter("status")));

      Member owner = new Member();
      owner.setNo(Integer.parseInt(request.getParameter("owner")));
      t.setOwner(owner);

      taskService.add(t);

      out.println("<meta http-equiv='Refresh' content='1;url=list'>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>작업 등록</h1>");
      out.println("<p>작업을 등록했습니다.</p>");

    } catch (Exception e) {
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);

      out.println("</head>");
      out.println("<body>");
      out.println("<h1>작업 등록 오류</h1>");
      out.printf("<pre>%s</pre>\n", strWriter.toString());
      out.println("<p><a href='list'>목록</a></p>");
    }

    out.println("</body>");
    out.println("</html>");
  }
}
