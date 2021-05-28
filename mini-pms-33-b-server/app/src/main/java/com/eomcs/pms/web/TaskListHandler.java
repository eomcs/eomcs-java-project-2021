package com.eomcs.pms.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;
import com.eomcs.util.Component;
import com.eomcs.util.PageController;

@Component("/task/list")
public class TaskListHandler implements PageController {

  TaskService taskService;
  ProjectService projectService;

  public TaskListHandler(TaskService taskService, ProjectService projectService) {
    this.taskService = taskService;
    this.projectService = projectService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

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
    return "/jsp/task/list.jsp";
  }
}
