package com.eomcs.pms.web;

import java.sql.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;

@Controller
@RequestMapping("/task/")
public class TaskController {

  TaskService taskService;
  ProjectService projectService;
  MemberService memberService;

  public TaskController(TaskService taskService, ProjectService projectService, MemberService memberService) {
    this.taskService = taskService;
    this.projectService = projectService;
    this.memberService = memberService;
  }

  @RequestMapping("add")
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

  @RequestMapping("delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Task task = taskService.get(no);
    if (task == null) {
      throw new Exception("해당 번호의 작업이 없습니다.");
    }

    taskService.delete(no);

    return "redirect:list";
  }

  @RequestMapping("detail")
  public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Task task = taskService.get(no);
    if (task == null) {
      throw new Exception("해당 번호의 작업이 없습니다.");
    }

    request.setAttribute("task", task);
    request.setAttribute("members", memberService.list(null));
    return "/jsp/task/detail.jsp";
  }

  @RequestMapping("list")
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {

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

  @RequestMapping("update")
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {

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
