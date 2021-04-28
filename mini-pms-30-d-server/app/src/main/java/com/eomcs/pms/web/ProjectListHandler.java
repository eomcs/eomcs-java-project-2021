package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@WebServlet("/project/list")
public class ProjectListHandler implements Servlet {

  @Override
  public void service(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

    ProjectService projectService = (ProjectService) request.getServletContext().getAttribute("projectService");

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("[프로젝트 목록]");

    try {
      List<Project> projects = projectService.list();

      for (Project p : projects) {

        // 1) 프로젝트의 팀원 목록 가져오기
        StringBuilder strBuilder = new StringBuilder();
        List<Member> members = p.getMembers();
        for (Member m : members) {
          if (strBuilder.length() > 0) {
            strBuilder.append("/");
          }
          strBuilder.append(m.getName());
        }

        // 2) 프로젝트 정보를 출력
        out.printf("%d, %s, %s, %s, %s, [%s]\n", 
            p.getNo(), 
            p.getTitle(), 
            p.getStartDate(),
            p.getEndDate(),
            p.getOwner().getName(),
            strBuilder.toString());

      } 

    }catch (Exception e) {
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);
      out.println(strWriter.toString());
    }
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
  }

  @Override
  public void destroy() {
  }

  @Override
  public ServletConfig getServletConfig() {
    return null;
  }

  @Override
  public String getServletInfo() {
    return null;
  }
}








