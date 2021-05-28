package com.eomcs.pms.web.servlet;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.util.PageController;

// 프론트 컨트롤러 역할을 수행할 서블릿
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/app/*")
@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {

  @SuppressWarnings("unchecked")
  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 어느 서블릿을 요청했는지 알아내기
    String controllerPath = request.getPathInfo();

    Map<String,Object> beanContainer = 
        (Map<String,Object>) this.getServletContext().getAttribute("beanContainer");

    // 클라이언트가 요청한 페이지 컨트롤러를 꺼낸다.
    PageController pageController = (PageController) beanContainer.get(controllerPath);
    if (pageController == null) {
      throw new ServletException("요청한 자원이 없습니다!");
    }

    response.setContentType("text/html;charset=UTF-8");

    try {
      // 페이지 컨트롤러에게 실행을 위임한다.
      String url = pageController.execute(request, response);

      // 페이지 컨트롤러의 리턴 URL이 리다이렉트를 요구한다면,
      if (url.startsWith("redirect:")) {
        response.sendRedirect(url.substring(9));
        return;
      }

      // 페이지 컨트롤러가 알려준 JSP를 인클루드 한다.
      request.getRequestDispatcher(url).include(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}








