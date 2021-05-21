package com.eomcs.pms.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

      request.setAttribute("projectNo", projectNo);
      request.setAttribute("projects", projectService.list());
      request.setAttribute("tasks", tasks);

      response.setContentType("text/html;charset=UTF-8");
      request.getRequestDispatcher("/jsp/task/list.jsp").include(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
