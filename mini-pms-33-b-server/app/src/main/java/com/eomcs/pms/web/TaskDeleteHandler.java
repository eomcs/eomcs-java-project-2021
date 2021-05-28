package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.TaskService;
import com.eomcs.util.Component;
import com.eomcs.util.PageController;

@Component("/task/delete")
public class TaskDeleteHandler implements PageController {

  TaskService taskService;

  public TaskDeleteHandler(TaskService taskService) {
    this.taskService = taskService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Task task = taskService.get(no);
    if (task == null) {
      throw new Exception("해당 번호의 작업이 없습니다.");
    }

    taskService.delete(no);

    return "redirect:list";
  }
}
