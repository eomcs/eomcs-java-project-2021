package com.eomcs.pms.web;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;

@SuppressWarnings("serial")
@WebServlet("/task/add")
public class TaskAddHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ProjectService projectService = (ProjectService) request.getServletContext().getAttribute("projectService");
    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

    try {
      request.setAttribute("projects", projectService.list());
      request.setAttribute("members", memberService.list(null));

      response.setContentType("text/html;charset=UTF-8");
      request.getRequestDispatcher("/jsp/task/form.jsp").include(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    TaskService taskService = (TaskService) request.getServletContext().getAttribute("taskService");

    try {
      Task t = new Task();
      t.setProjectNo(Integer.parseInt(request.getParameter("projectNo")));
      t.setContent(request.getParameter("content"));
      t.setDeadline(Date.valueOf(request.getParameter("deadline")));
      t.setStatus(Integer.parseInt(request.getParameter("status")));

      Member owner = new Member();
      owner.setNo(Integer.parseInt(request.getParameter("owner")));
      t.setOwner(owner);

      taskService.add(t);

      response.sendRedirect("list");

    } catch (Exception e) {
      throw new ServletException(e);
    }

  }
}
