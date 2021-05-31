package com.eomcs.pms.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@Controller
public class ProjectListHandler {

  ProjectService projectService;

  public ProjectListHandler(ProjectService projectService) {
    this.projectService = projectService;
  }

  @RequestMapping("/project/list")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    List<Project> projects = null;

    String item = request.getParameter("item");
    String keyword = request.getParameter("keyword");
    String title = request.getParameter("title");
    String owner = request.getParameter("owner");
    String member = request.getParameter("member");

    if (item != null && keyword != null && keyword.length() > 0) {
      projects = projectService.search(item, keyword);

    } else if ((title != null && title.length() > 0) ||
        (owner != null && owner.length() > 0) ||
        (member != null && member.length() > 0)) {
      projects = projectService.search(title, owner, member);

    } else {
      projects = projectService.list();
    }

    request.setAttribute("projects", projects);
    return "/jsp/project/list.jsp";
  }
}








