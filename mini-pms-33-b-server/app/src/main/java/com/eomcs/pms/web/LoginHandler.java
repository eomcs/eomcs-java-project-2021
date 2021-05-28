package com.eomcs.pms.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.util.Component;
import com.eomcs.util.PageController;

@Component("/login")
public class LoginHandler implements PageController {

  MemberService memberService;

  public LoginHandler(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    if (request.getMethod().equals("GET")) {
      return "/jsp/login_form.jsp";
    } 

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

    Member member = memberService.get(email, password);

    if (member == null) {
      request.getSession().invalidate(); 
      return "/jsp/login_fail.jsp";

    } else {
      request.getSession().setAttribute("loginUser", member);
      return "/jsp/login_success.jsp";
    }
  }
}






