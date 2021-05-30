package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;

@Controller
public class ProjectDetailHandler {

  ProjectService projectService;
  MemberService memberService;

  public ProjectDetailHandler(ProjectService projectService, MemberService memberService) {
    this.projectService = projectService;
    this.memberService = memberService;
  }

  @RequestMapping("/project/detail")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Project project = projectService.get(no);
    if (project == null) {
      throw new Exception("해당 번호의 프로젝트가 없습니다.");
    }

    //      MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");
    //      List<Member> members = memberService.list(null);
    //      for (Member m : members) {
    //        out.printf("  <input type='checkbox' name='member' value='%d' %s>%s<br>\n", 
    //            m.getNo(), contain(project.getMembers(), m.getNo()) ? "checked" : "", m.getName());
    //      }

    request.setAttribute("project", project);
    request.setAttribute("projectMembers", project.getMembers());
    request.setAttribute("members", memberService.list(null));
    return "/jsp/project/detail.jsp";
  }
}








