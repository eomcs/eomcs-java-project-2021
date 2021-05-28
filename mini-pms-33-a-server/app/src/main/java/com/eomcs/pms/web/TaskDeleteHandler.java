package com.eomcs.pms.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.TaskService;

@SuppressWarnings("serial")
@WebServlet("/task/delete")
public class TaskDeleteHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    TaskService taskService = (TaskService) request.getServletContext().getAttribute("taskService");

    try {
      int no = Integer.parseInt(request.getParameter("no"));

      Task task = taskService.get(no);
      if (task == null) {
        throw new Exception("해당 번호의 작업이 없습니다.");
      }

      taskService.delete(no);

      request.setAttribute("redirect", "list");

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
