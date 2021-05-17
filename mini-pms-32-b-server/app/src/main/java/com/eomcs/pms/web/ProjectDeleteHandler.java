package com.eomcs.pms.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@SuppressWarnings("serial")
@WebServlet("/project/delete")
public class ProjectDeleteHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ProjectService projectService = (ProjectService) request.getServletContext().getAttribute("projectService");

    try {
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

      response.sendRedirect("list");

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}








