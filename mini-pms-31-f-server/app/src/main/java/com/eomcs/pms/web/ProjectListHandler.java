package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet("/project/list")
public class ProjectListHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ProjectService projectService = (ProjectService) request.getServletContext().getAttribute("projectService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>프로젝트</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>프로젝트</h1>");

    out.println("<p><a href='add1'>새 프로젝트</a></p>");

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

      out.println("<table border='1'>");
      out.println("<thead>");
      out.println("<tr>");
      out.println("<th>번호</th> <th>프로젝트명</th> <th>기간</th> <th>팀장</th> <th>팀원</th>");
      out.println("</tr>");
      out.println("</thead>");
      out.println("<tbody>");

      for (Project p : projects) {

        StringBuilder strBuilder = new StringBuilder();
        List<Member> members = p.getMembers();
        for (Member m : members) {
          if (strBuilder.length() > 0) {
            strBuilder.append(",");
          }
          strBuilder.append(m.getName());
        }

        out.printf("<tr>"
            + " <td>%d</td>"
            + " <td><a href='detail?no=%1$d'>%s</a></td>"
            + " <td>%s ~ %s</td>"
            + " <td>%s</td>"
            + " <td>%s</td> </tr>\n", 
            p.getNo(), 
            p.getTitle(), 
            p.getStartDate(),
            p.getEndDate(),
            p.getOwner().getName(),
            strBuilder.toString());
      } 
      out.println("</tbody>");
      out.println("</table>");

      out.println("<form method='get'>");
      out.println("<select name='item'>");
      out.printf("  <option value='0' %s>전체</option>\n", 
          (item != null && item.equals("0")) ? "selected" : "");
      out.printf("  <option value='1' %s>프로젝트명</option>\n", 
          (item != null && item.equals("1")) ? "selected" : "");
      out.printf("  <option value='2' %s>관리자</option>\n", 
          (item != null && item.equals("2")) ? "selected" : "");
      out.printf("  <option value='3' %s>팀원</option>\n", 
          (item != null && item.equals("3")) ? "selected" : "");
      out.println("</select>");
      out.printf("<input type='search' name='keyword' value='%s'> \n",
          keyword != null ? keyword : "");
      out.println("<button>검색</button>");
      out.println("</form>");

      out.println("<form method='get'>");
      out.println("<fieldset>");
      out.println("  <legend>상세 검색</legend>");
      out.println("  <table border='1'>");
      out.println("  <tbody>");
      out.printf("  <tr> <th>프로젝트명</th>"
          + " <td><input type='search' name='title' value='%s'></td> </tr>\n", 
          title != null ? title : "");
      out.printf("  <tr> <th>관리자</th>"
          + " <td><input type='search' name='owner' value='%s'></td> </tr>\n", 
          owner != null ? owner : "");
      out.printf("  <tr> <th>팀원</th>"
          + " <td><input type='search' name='member' value='%s'></td> </tr>\n", 
          member != null ? member : "");
      out.println("  <tr> <td colspan='2'><button>검색</button></td> </tr>");
      out.println("  </tbody>");
      out.println("  </table>");
      out.println("</fieldset>");
      out.println("</form>");

    } catch (Exception e) {
      throw new ServletException(e);
    }

    out.println("</body>");
    out.println("</html>");
  }
}








