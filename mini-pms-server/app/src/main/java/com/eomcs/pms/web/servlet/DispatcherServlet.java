package com.eomcs.pms.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 프론트 컨트롤러 역할을 수행할 서블릿
@WebServlet("/app/*")
@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 어느 서블릿을 요청했는지 알아내기
    String controllerPath = request.getPathInfo();

    // 해당 페이지 컨트롤러로 위임한다.
    response.setContentType("text/html;charset=UTF-8");
    request.getRequestDispatcher(controllerPath).include(request, response);

    // 페이지 컨트롤러가 알려준 리다이렉트 URL이 있다면 클라이언트에게 응답한다.
    String redirectUrl = (String) request.getAttribute("redirect");
    if (redirectUrl != null) {
      response.sendRedirect(redirectUrl);
      return;
    }

    // 페이지 컨트롤러가 알려준 JSP를 인클루드 한다.
    // - 페이지 컨트롤러는 작업을 수행한 후 그 결과를 출력할 JSP 주소를 ServletRequest 보관소에 저장해 둘 것이다.
    String viewUrl = (String) request.getAttribute("viewUrl");
    request.getRequestDispatcher(viewUrl).include(request, response);

  }
}








