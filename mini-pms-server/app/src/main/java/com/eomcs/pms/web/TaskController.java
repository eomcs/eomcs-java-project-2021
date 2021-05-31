package com.eomcs.pms.web;

import java.sql.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

  @GetMapping("add")
  public String form(Model model) throws Exception {
    model.addAttribute("projects", projectService.list());
    model.addAttribute("members", memberService.list(null));
    return "/jsp/task/form.jsp";
  }

  @PostMapping("add")
  public String add(Task task, int ownerNo) throws Exception {
    Member owner = new Member();
    owner.setNo(ownerNo);
    task.setOwner(owner);

    taskService.add(task);

    return "redirect:list";
  }

  @RequestMapping("delete")
  public String delete(int no) throws Exception {
    if (taskService.delete(no) == 0) {
      throw new Exception("해당 번호의 작업이 없습니다.");
    }
    return "redirect:list";
  }

  @GetMapping("detail")
  public String detail(int no, Model model) throws Exception {
    Task task = taskService.get(no);
    if (task == null) {
      throw new Exception("해당 번호의 작업이 없습니다.");
    }

    model.addAttribute("task", task);
    model.addAttribute("members", memberService.list(null));
    return "/jsp/task/detail.jsp";
  }

  @GetMapping("list")
  public String list(int projectNo, Model model) throws Exception {
    List<Task> tasks = null;
    if (projectNo == 0) {
      tasks = taskService.list();
    } else {
      tasks = taskService.listOfProject(projectNo);
    }

    model.addAttribute("projectNo", projectNo);
    model.addAttribute("projects", projectService.list());
    model.addAttribute("tasks", tasks);
    return "/jsp/task/list.jsp";
  }

  @PostMapping("update")
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
