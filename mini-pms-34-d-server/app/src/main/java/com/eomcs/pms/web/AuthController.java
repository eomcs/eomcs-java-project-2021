package com.eomcs.pms.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
  public String form() throws Exception {
    return "/jsp/login_form.jsp";
  }

  @PostMapping("/login")
  public String login(
      String email, String password, String saveEmail, 
      HttpSession session, HttpServletResponse response) throws Exception {

    if (saveEmail != null) {
      Cookie cookie = new Cookie("email", email);
      cookie.setMaxAge(60 * 60 * 24 * 5); 
      response.addCookie(cookie);
    } else {
      Cookie cookie = new Cookie("email", "");
      cookie.setMaxAge(0); 
      response.addCookie(cookie);
    }

    Member member = memberService.get(email, password);

    if (member == null) {
      session.invalidate(); 
      return "/jsp/login_fail.jsp";

    } else {
      session.setAttribute("loginUser", member);
      return "/jsp/login_success.jsp";
    }
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) throws Exception {
    session.invalidate();
    return "redirect:login";
  }

  @GetMapping("/userInfo")
  public String userInfo() throws Exception {
    return "/jsp/user_info.jsp";
  }
}






