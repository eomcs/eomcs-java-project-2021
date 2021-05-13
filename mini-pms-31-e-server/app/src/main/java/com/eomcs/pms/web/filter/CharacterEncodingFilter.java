package com.eomcs.pms.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

// POST 요청 파라미터에 대해 문자 집합을 설정하는 필터
//
//@WebFilter(value="/*") => web.xml 에 필터 배치 정보를 설정한다. 
public class CharacterEncodingFilter implements Filter {

  String encoding; 

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.encoding = filterConfig.getInitParameter("encoding");
    if (encoding == null) {
      encoding = "UTF-8";
    }
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;

    if (httpRequest.getMethod().equals("POST")) {
      httpRequest.setCharacterEncoding(this.encoding);
    }

    chain.doFilter(request, response);


  }

}








