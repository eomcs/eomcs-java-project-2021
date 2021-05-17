package com.eomcs.pms.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@SuppressWarnings("serial")
@WebServlet("/project/list")
public class ProjectListHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ProjectService projectService = (ProjectService) request.getServletContext().getAttribute("projectService");

    try {
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

      response.setContentType("text/html;charset=UTF-8");
      request.getRequestDispatcher("/jsp/project/list.jsp").include(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}








