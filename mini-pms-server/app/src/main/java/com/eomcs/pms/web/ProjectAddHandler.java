package com.eomcs.pms.web;

import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@Controller
public class ProjectAddHandler {

  ProjectService projectService;

  public ProjectAddHandler(ProjectService projectService) {
    this.projectService = projectService;
  }

  @RequestMapping("/project/add")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    HttpSession session = request.getSession();

    Project p = new Project();
    p.setTitle((String) session.getAttribute("title"));
    p.setContent((String) session.getAttribute("content"));
    p.setStartDate(Date.valueOf((String) session.getAttribute("startDate")));
    p.setEndDate(Date.valueOf((String) session.getAttribute("endDate")));

    Member loginUser = (Member) session.getAttribute("loginUser");
    p.setOwner(loginUser);

    String[] values = request.getParameterValues("member");
    ArrayList<Member> memberList = new ArrayList<>();
    if (values != null) {
      for (String value : values) {
        Member member = new Member();
        member.setNo(Integer.parseInt(value));
        memberList.add(member);
      }
    }
    p.setMembers(memberList);

    projectService.add(p);

    return "redirect:list";
  }
}








