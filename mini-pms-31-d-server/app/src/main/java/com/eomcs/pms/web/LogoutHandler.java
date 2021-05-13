package com.eomcs.pms.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/logout")
public class LogoutHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.getSession().invalidate();

    // 로그아웃 한다면 뭘 출력해도 아무런 의미가 없다.
    // 그냥 다시 로그인 화면으로 보내라!
    // 이럴 때 사용하는 것이 리다이렉트이다.
    //
    response.sendRedirect("login");
  }
}






