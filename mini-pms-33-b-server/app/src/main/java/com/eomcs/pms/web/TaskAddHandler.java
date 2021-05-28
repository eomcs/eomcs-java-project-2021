package com.eomcs.pms.web;

import java.sql.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;
import com.eomcs.util.Component;
import com.eomcs.util.PageController;

@Component("/task/add")
public class TaskAddHandler implements PageController {

  TaskService taskService;
  ProjectService projectService;
  MemberService memberService;

  public TaskAddHandler(TaskService taskService, ProjectService projectService, MemberService memberService) {
    this.taskService = taskService;
    this.projectService = projectService;
    this.memberService = memberService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      request.setAttribute("projects", projectService.list());
      request.setAttribute("members", memberService.list(null));
      return "/jsp/task/form.jsp";
    }

    Task t = new Task();
    t.setProjectNo(Integer.parseInt(request.getParameter("projectNo")));
    t.setContent(request.getParameter("content"));
    t.setDeadline(Date.valueOf(request.getParameter("deadline")));
    t.setStatus(Integer.parseInt(request.getParameter("status")));

    Member owner = new Member();
    owner.setNo(Integer.parseInt(request.getParameter("owner")));
    t.setOwner(owner);

    taskService.add(t);

    return "redirect:list";
  }
}
