package com.eomcs.pms.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@SuppressWarnings("serial")
@WebServlet("/login")
public class LoginHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 클라이언트가 보낸 쿠키 값 중에서 email 이름의 값을 꺼낸다.
    String email = "";

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("email")) {
          email = cookie.getValue();
          break;
        }
      }
    }

    response.setContentType("text/html;charset=UTF-8");

    request.setAttribute("email", email);
    response.setContentType("text/html;charset=UTF-8");
    request.getRequestDispatcher("/jsp/login_form.jsp").include(request, response);

  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

    String email = request.getParameter("email");
    String password = request.getParameter("password");

    // 클라이언트에게 쿠키를 보내기
    if (request.getParameter("saveEmail") != null) {
      Cookie cookie = new Cookie("email", email);
      cookie.setMaxAge(60 * 60 * 24 * 5); // 유효기간을 설정하지 않으면 웹브라우저가 실행되는 동안만 유지하라는 의미가 된다.
      response.addCookie(cookie);
    } else {
      // 기존에 있는 쿠키도 제거한다.
      Cookie cookie = new Cookie("email", "");
      cookie.setMaxAge(0);  // 유효기간(초)을 0으로 하면 웹브라우저는 email 이름으로 저장된 쿠키를 제거한다.
      response.addCookie(cookie);
    }

    try {
      Member member = memberService.get(email, password);

      response.setContentType("text/html;charset=UTF-8");

      if (member == null) {
        // 로그인 실패한다면 세션 객체의 모든 내용을 삭제한다.
        request.getSession().invalidate(); 
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/jsp/login_fail.jsp").include(request, response);
        response.setHeader("Refresh", "1;url=login");

      } else {
        // 로그인 성공한다면, 로그인 사용자 정보를 세션 객체에 보관한다.
        request.getSession().setAttribute("loginUser", member);

        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/jsp/login_success.jsp").include(request, response);
        response.setHeader("Refresh", "1;url=userInfo");
      }
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}






