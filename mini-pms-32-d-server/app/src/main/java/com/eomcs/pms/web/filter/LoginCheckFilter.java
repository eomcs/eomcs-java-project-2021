package com.eomcs.pms.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class LoginCheckFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain nextChain)
      throws ServletException, IOException {

    // 파라미터로 받은 request를 본래 타입으로 형변환 한다.
    HttpServletRequest httpRequest = (HttpServletRequest) request;

    String servletPath = httpRequest.getServletPath().toLowerCase();

    if (servletPath.endsWith("add") ||   // 예) /board/add
        servletPath.endsWith("update") ||  // 예) /board/update, /project/memberUpdate
        servletPath.endsWith("delete")) { //  예) /board/delete, /project/memberDelete

      if (httpRequest.getSession().getAttribute("loginUser") == null) {
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().println("로그인 해야 합니다!");
        return;
      }
    }

    // 로그인이 필요한 서비스에 대해서 정상적으로 로그인 되어 있다면, 다음 필터를 실행한다.\
    nextChain.doFilter(request, response);

  }
}
