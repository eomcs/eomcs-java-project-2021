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
import com.eomcs.pms.service.TaskService;

@SuppressWarnings("serial")
@WebServlet("/task/update")
public class TaskUpdateHandler extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    TaskService taskService = (TaskService) request.getServletContext().getAttribute("taskService");

    try {
      int no = Integer.parseInt(request.getParameter("no"));

      Task oldTask = taskService.get(no);
      if (oldTask == null) {
        throw new Exception("해당 번호의 회원이 없습니다.");
      } 

      Task task = new Task();
      task.setNo(no);
      task.setProjectNo(Integer.parseInt(request.getParameter("projectNo")));
      task.setContent(request.getParameter("content"));
      task.setDeadline(Date.valueOf(request.getParameter("deadline")));
      //신규(0), 진행중(1), 완료(2)
      task.setStatus(Integer.parseInt(request.getParameter("status")));

      Member owner = new Member();
      owner.setNo(Integer.parseInt(request.getParameter("owner")));
      task.setOwner(owner);

      taskService.update(task);

      response.sendRedirect("list");

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
