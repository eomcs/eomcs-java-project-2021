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
import com.eomcs.pms.service.MemberService;

// 이 서블릿은 프로젝트의 멤버를 선택하거나 현재 프로젝트 멤버를 출력하는 일을 한다.
// 직접 사용되지는 않고, 다른 서블릿의 요청을 보조하는 역할을 한다.
// 즉 인클루딩 목적으로 만든 서블릿이다.
//
@SuppressWarnings("serial")
@WebServlet("/project/member/list")
public class ProjectMemberListHandler extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

    // 인클루딩에서 사용되는 서블릿은 출력 콘텐트 타입을 설정할 필요가 없다.
    // 설정해도 소용이 없다.
    // 왜? 이 서블릿을 인클루딩하는 서블릿 쪽에서 설정하기 때문이다.
    // 
    // response.setContentType("text/html;charset=UTF-8");

    PrintWriter out = response.getWriter();

    try {
      List<Member> members = memberService.list(null);

      @SuppressWarnings("unchecked")
      List<Member> projectMembers = (List<Member>) request.getAttribute("members"); 

      for (Member m : members) {
        if (projectMembers != null) {
          out.printf("  <input type='checkbox' name='member' value='%d' %s>%s<br>\n", 
              m.getNo(), contain(projectMembers, m.getNo()) ? "checked" : "", m.getName());
        } else {
          out.printf("  <input type='checkbox' name='member' value='%d'>%s<br>\n", m.getNo(), m.getName());
        }
      }
    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error").forward(request, response);
      return;
    }
  }

  private boolean contain(List<Member> members, int memberNo) {
    for (Member m : members) {
      if (m.getNo() == memberNo) {
        return true;
      }
    }
    return false;
  }
}








