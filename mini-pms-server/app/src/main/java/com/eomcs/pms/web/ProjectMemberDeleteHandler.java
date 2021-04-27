package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@SuppressWarnings("serial")
@WebServlet("/project/memberDelete")
public class ProjectMemberDeleteHandler extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ProjectService projectService = (ProjectService) request.getServletContext().getAttribute("projectService");

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("[프로젝트 멤버 삭제]");

    try {
      int no = Integer.parseInt(request.getParameter("no"));

      Project project = projectService.get(no);

      if (project == null) {
        out.println("해당 번호의 프로젝트가 없습니다.");
        return;
      }

      Member loginUser = (Member) request.getSession().getAttribute("loginUser");
      if (project.getOwner().getNo() != loginUser.getNo()) {
        out.println("삭제 권한이 없습니다!");
        return;
      }

      // 프로젝트의 기존 멤버를 모두 삭제한다.
      projectService.deleteMembers(no);

      out.println("프로젝트 멤버를 삭제하였습니다.");

    } catch (Exception e) {
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);
      out.println(strWriter.toString());
    }
  }
}






