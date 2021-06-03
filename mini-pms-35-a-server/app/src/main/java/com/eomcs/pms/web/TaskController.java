package com.eomcs.pms.web;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("form")
  public void form(Model model) throws Exception {
    model.addAttribute("projects", projectService.list());
    model.addAttribute("members", memberService.list(null));
  }

  @PostMapping("add")
  public String add(Task task) throws Exception {
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
  public void detail(int no, Model model) throws Exception {
    Task task = taskService.get(no);
    if (task == null) {
      throw new Exception("해당 번호의 작업이 없습니다.");
    }

    model.addAttribute("task", task);
    model.addAttribute("members", memberService.list(null));
  }

  @GetMapping("list")
  public void list(@RequestParam(defaultValue = "0") int projectNo, Model model) throws Exception {

    List<Task> tasks = null;
    if (projectNo == 0) {
      tasks = taskService.list();
    } else {
      tasks = taskService.listOfProject(projectNo);
    }

    model.addAttribute("projectNo", projectNo);
    model.addAttribute("projects", projectService.list());
    model.addAttribute("tasks", tasks);
  }

  @PostMapping("update")
  public String update(Task task) throws Exception {
    if (taskService.update(task) == 0) {
      throw new Exception("해당 번호의 회원이 없습니다.");
    } 
    return "redirect:list";
  }
}
