package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@SuppressWarnings("serial")
@WebServlet("/project/search")
public class ProjectSearchHandler extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ProjectService projectService = (ProjectService) request.getServletContext().getAttribute("projectService");

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("[프로젝트 검색]");

    try {
      // 항목(1:프로젝트명, 2:관리자명, 3:팀원, 그 외: 전체)
      String item = request.getParameter("item");
      String keyword = null;
      if (item.equals("1") || 
          item.equals("2") || 
          item.equals("3")) {
        keyword = request.getParameter("keyword");
      }

      List<Project> projects = projectService.search(item, keyword);

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

    } catch (Exception e) {
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);
      out.println(strWriter.toString());
    }
  }
}








