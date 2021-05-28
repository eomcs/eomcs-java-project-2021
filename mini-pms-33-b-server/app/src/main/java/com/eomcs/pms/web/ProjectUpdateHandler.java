package com.eomcs.pms.web;

import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.util.Component;
import com.eomcs.util.PageController;

@Component("/project/update")
public class ProjectUpdateHandler implements PageController {

  ProjectService projectService;

  public ProjectUpdateHandler(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Project oldProject = projectService.get(no);

    if (oldProject == null) {
      throw new Exception("해당 번호의 프로젝트가 없습니다.");
    } 

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (oldProject.getOwner().getNo() != loginUser.getNo()) {
      throw new Exception("변경 권한이 없습니다!");
    }

    // 사용자에게서 변경할 데이터를 입력 받는다.
    Project project = new Project();
    project.setNo(no);
    project.setTitle(request.getParameter("title"));
    project.setContent(request.getParameter("content"));
    project.setStartDate(Date.valueOf(request.getParameter("startDate")));
    project.setEndDate(Date.valueOf(request.getParameter("endDate")));
    project.setOwner(loginUser);

    // ...&member=1&member=18&member=23
    String[] values = request.getParameterValues("member");
    ArrayList<Member> memberList = new ArrayList<>();
    if (values != null) {
      for (String value : values) {
        Member member = new Member();
        member.setNo(Integer.parseInt(value));
        memberList.add(member);
      }
    }
    project.setMembers(memberList);

    // DBMS에게 프로젝트 변경을 요청한다.
    projectService.update(project);

    return "redirect:list";
  }
}






