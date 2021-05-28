package com.eomcs.pms.web;

import java.sql.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.TaskService;
import com.eomcs.util.Component;
import com.eomcs.util.PageController;

@Component("/task/update")
public class TaskUpdateHandler implements PageController {

  TaskService taskService;

  public TaskUpdateHandler(TaskService taskService) {
    this.taskService = taskService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Task oldTask = taskService.get(no);
    if (oldTask == null) {
      throw new Exception("해당 번호의 회원이 없습니다.");
    } 

    Task task = new Task();
    task.setNo(no);
    task.setProjectNo(Integer.parseInt(request.getParameter("projectNo")));
    task.setContent(request.getParameter("content"));
    task.setDeadline(Date.valueOf(request.getParameter("deadline")));
    //신규(0), 진행중(1), 완료(2)
    task.setStatus(Integer.parseInt(request.getParameter("status")));

    Member owner = new Member();
    owner.setNo(Integer.parseInt(request.getParameter("owner")));
    task.setOwner(owner);

    taskService.update(task);

    return "redirect:list";
  }
}
