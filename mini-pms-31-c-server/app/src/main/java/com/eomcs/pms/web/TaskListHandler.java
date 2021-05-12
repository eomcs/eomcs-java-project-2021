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
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;

@SuppressWarnings("serial")
@WebServlet("/task/list")
public class TaskListHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ProjectService projectService = (ProjectService) request.getServletContext().getAttribute("projectService");
    TaskService taskService = (TaskService) request.getServletContext().getAttribute("taskService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>작업</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>작업</h1>");

    out.println("<p><a href='add'>새 작업</a></p>");

    try {
      String input  = request.getParameter("projectNo");

      int projectNo = 0;
      if (input != null) {
        try {
          projectNo = Integer.parseInt(input);
        } catch (Exception e) {
          throw new Exception("유효한 프로젝트 번호를 입력하세요.");
        }
      }

      List<Task> tasks = null;
      if (projectNo == 0) {
        tasks = taskService.list();
      } else {
        tasks = taskService.listOfProject(projectNo);
      }

      out.println("<form>");
      out.println("프로젝트: <select name='projectNo'>");
      out.println("  <option value='0' selected>전체</option>");
      List<Project> projects = projectService.list();
      for (Project p : projects) {
        out.printf("  <option value='%d' %s>%s</option>\n", 
            p.getNo(), 
            projectNo == p.getNo() ? "selected" : "",
                p.getTitle());
      }
      out.println("</select>");
      out.println("<button>검색</button>");
      out.println("</form>");

      out.println("<table border='1'>");
      out.println("<thead>");
      out.println("<tr>");
      out.println("<th>번호</th> <th>작업</th> <th>마감일</th> <th>담당자</th> <th>상태</th>");
      out.println("</tr>");
      out.println("</thead>");
      out.println("<tbody>");

      if (tasks.size() == 0) {
        out.println("<tr><td colspan='5'>해당 번호의 프로젝트가 없거나 또는 등록된 작업이 없습니다.</td></tr>");

      } else {
        projectNo = 0;
        for (Task t : tasks) {
          if (projectNo != t.getProjectNo()) {
            out.printf("<tr><td colspan='5'>프로젝트: '%s'</td></tr>\n", t.getProjectTitle());
            projectNo = t.getProjectNo();
          }
          out.printf("<tr>"
              + " <td>%d</td>"
              + " <td><a href='detail?no=%1$d'>%s</a></td>"
              + " <td>%s</td>"
              + " <td>%s</td>"
              + " <td>%s</td> </tr>\n", 
              t.getNo(), 
              t.getContent(), 
              t.getDeadline(),
              t.getOwner().getName(),
              Task.getStatusLabel(t.getStatus()));
        }
        out.println("</tbody>");
        out.println("</table>");
      }
    } catch (Exception e) {
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);

      out.printf("<p>%s</p>\n", e.getMessage());
      out.printf("<pre>%s</pre>\n", strWriter.toString());
    }

    out.println("</body>");
    out.println("</html>");
  }
}
