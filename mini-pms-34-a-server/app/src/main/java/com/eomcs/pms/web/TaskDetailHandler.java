package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.TaskService;

@Controller
public class TaskDetailHandler {

  TaskService taskService;
  MemberService memberService;

  public TaskDetailHandler(TaskService taskService, MemberService memberService) {
    this.taskService = taskService;
    this.memberService = memberService;
  }

  @RequestMapping("/task/detail")
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
