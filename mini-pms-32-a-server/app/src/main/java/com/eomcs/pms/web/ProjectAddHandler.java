package com.eomcs.pms.web;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@SuppressWarnings("serial")
@WebServlet("/project/add")
public class ProjectAddHandler extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ProjectService projectService = (ProjectService) request.getServletContext().getAttribute("projectService");

    HttpSession session = request.getSession();

    try {
      Project p = new Project();
      // 프로젝트 등록 1단계에서 입력한 내용이 세션에 보관되어 있다. 
      p.setTitle((String) session.getAttribute("title"));

      // 프로젝트 등록 2단계에서 입력한 내용이 세션에 보관되어 있다.
      p.setContent((String) session.getAttribute("content"));
      p.setStartDate(Date.valueOf((String) session.getAttribute("startDate")));
      p.setEndDate(Date.valueOf((String) session.getAttribute("endDate")));

      // 사용자 로그인 정보는 세션에 보관되어 있다.
      Member loginUser = (Member) session.getAttribute("loginUser");
      p.setOwner(loginUser);

      // 프로젝트 팀원 정보는 파라미터에 있다.
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
      p.setMembers(memberList);

      projectService.add(p);

      response.sendRedirect("list");

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}








