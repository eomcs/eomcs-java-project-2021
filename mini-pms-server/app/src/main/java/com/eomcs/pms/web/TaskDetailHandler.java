package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.TaskService;

@SuppressWarnings("serial")
@WebServlet("/task/detail")
public class TaskDetailHandler extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    TaskService taskService = (TaskService) request.getServletContext().getAttribute("taskService");

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("[작업 상세보기]");

    try {
      int no = Integer.parseInt(request.getParameter("no"));

      Task task = taskService.get(no);

      if (task == null) {
        out.println("해당 번호의 작업이 없습니다.");
        return;
      }

      out.printf("프로젝트: %s\n", task.getProjectTitle());
      out.printf("내용: %s\n", task.getContent());
      out.printf("마감일: %s\n", task.getDeadline());
      out.printf("상태: %s\n", Task.getStatusLabel(task.getStatus()));
      out.printf("담당자: %s\n", task.getOwner().getName());

    } catch (Exception e) {
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);
      out.println(strWriter.toString());
    }
  }
}
