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
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.TaskService;

@SuppressWarnings("serial")
@WebServlet("/task/detail")
public class TaskDetailHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    TaskService taskService = (TaskService) request.getServletContext().getAttribute("taskService");
    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>작업</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>작업 정보</h1>");

    try {
      int no = Integer.parseInt(request.getParameter("no"));

      Task task = taskService.get(no);

      if (task == null) {
        out.println("<p>해당 번호의 작업이 없습니다.</p>");
        out.println("</body>");
        out.println("</html>");
        return;
      }

      out.println("<form action='update' method='post'>");
      out.printf("<input type='hidden' name='projectNo' value='%d'>\n", task.getProjectNo());
      out.println("<table border='1'>");
      out.println("<tbody>");
      out.printf("<tr><th>프로젝트</th> <td>%s</td></tr>\n", task.getProjectTitle());
      out.printf("<tr><th>번호</th>"
          + " <td><input name='no' type='text' value='%d' readonly></td></tr>\n", task.getNo());
      out.printf("<tr><th>작업</th>"
          + " <td><input name='content' type='text' value='%s'></td></tr>\n", task.getContent());
      out.printf("<tr><th>마감일</th>"
          + " <td><input name='deadline' type='date' value='%s'></td></tr>\n", task.getDeadline());

      out.println("<tr><th>상태</th> <td>");
      out.printf("<input type='radio' name='status' value='0' %s>신규 \n",
          task.getStatus() == 0 ? "checked" : "");
      out.printf("<input type='radio' name='status' value='1' %s>진행중 \n",
          task.getStatus() == 1 ? "checked" : "");
      out.printf("<input type='radio' name='status' value='2' %s>완료 </td></tr>\n",
          task.getStatus() == 2 ? "checked" : "");

      out.println("<tr><th>담당자</th> <td><select name='owner'>");
      List<Member> members = memberService.list(null);
      for (Member m : members) {
        out.printf("  <option value='%d' %s>%s</option>\n", 
            m.getNo(),
            task.getOwner().getNo() == m.getNo() ? "selected" : "",
                m.getName());
      }
      out.println("</select><br>");

      out.println("</tbody>");

      out.println("<tfoot>");
      out.println("<tr><td colspan='2'>");
      out.println("<input type='submit' value='변경'> "
          + "<a href='delete?no=" + task.getNo() + "'>삭제</a> ");
      out.println("</td></tr>");
      out.println("</tfoot>");

      out.println("</table>");
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
}
