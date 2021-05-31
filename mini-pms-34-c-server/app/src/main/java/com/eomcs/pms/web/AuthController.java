package com.eomcs.pms.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@Controller
public class AuthController {

  MemberService memberService;

  public AuthController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/login")
  public String form(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/jsp/login_form.jsp";
  }

  @PostMapping("/login")
  public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

  @GetMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.getSession().invalidate();
    return "redirect:login";
  }

  @GetMapping("/userInfo")
  public String userInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/jsp/user_info.jsp";
  }
}






