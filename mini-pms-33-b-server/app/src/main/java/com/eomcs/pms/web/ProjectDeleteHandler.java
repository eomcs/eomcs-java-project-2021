package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.util.Component;
import com.eomcs.util.PageController;

@Component("/project/delete")
public class ProjectDeleteHandler implements PageController {

  ProjectService projectService;

  public ProjectDeleteHandler(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Project oldProject = projectService.get(no);

    if (oldProject == null) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    }

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (oldProject.getOwner().getNo() != loginUser.getNo()) {
      throw new Exception("삭제 권한이 없습니다!");
    }

    projectService.delete(no);

    return "redirect:list";
  }
}








