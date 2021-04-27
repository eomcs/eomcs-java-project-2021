package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@SuppressWarnings("serial")
@WebServlet("/project/add")
public class ProjectAddHandler extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ProjectService projectService = (ProjectService) request.getServletContext().getAttribute("projectService");

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("[프로젝트 등록]");

    try {
      Project p = new Project();
      p.setTitle(request.getParameter("title"));
      p.setContent(request.getParameter("content"));
      p.setStartDate(Date.valueOf(request.getParameter("startDate")));
      p.setEndDate(Date.valueOf(request.getParameter("endDate")));

      Member loginUser = (Member) request.getSession().getAttribute("loginUser");
      p.setOwner(loginUser);

      // ...&member=1&member=18&member=23
      String[] values = request.getParameterValues("member");
      ArrayList<Member> memberList = new ArrayList<>();
      for (String value : values) {
        Member member = new Member();
        member.setNo(Integer.parseInt(value));
        memberList.add(member);
      }
      p.setMembers(memberList);

      projectService.add(p);

      out.println("프로젝트를 등록했습니다.");

    } catch (Exception e) {
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);
      out.println(strWriter.toString());
    }
  }
}








