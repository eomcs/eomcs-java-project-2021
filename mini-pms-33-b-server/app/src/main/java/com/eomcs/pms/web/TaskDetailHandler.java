package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.TaskService;
import com.eomcs.util.Component;
import com.eomcs.util.PageController;

@Component("/task/detail")
public class TaskDetailHandler implements PageController {

  TaskService taskService;
  MemberService memberService;

  public TaskDetailHandler(TaskService taskService, MemberService memberService) {
    this.taskService = taskService;
    this.memberService = memberService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Task task = taskService.get(no);
    if (task == null) {
      throw new Exception("해당 번호의 작업이 없습니다.");
    }

    request.setAttribute("task", task);
    request.setAttribute("members", memberService.list(null));
    return "/jsp/task/detail.jsp";
  }
}
