package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.TaskService;

@Controller
public class TaskDeleteHandler {

  TaskService taskService;

  public TaskDeleteHandler(TaskService taskService) {
    this.taskService = taskService;
  }

  @RequestMapping("/task/delete")
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
