package com.eomcs.pms.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;

@SuppressWarnings("serial")
@WebServlet("/project/detail")
public class ProjectDetailHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ProjectService projectService = (ProjectService) request.getServletContext().getAttribute("projectService");
    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

    try {
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

      response.setContentType("text/html;charset=UTF-8");
      request.getRequestDispatcher("/jsp/project/detail.jsp").include(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}








