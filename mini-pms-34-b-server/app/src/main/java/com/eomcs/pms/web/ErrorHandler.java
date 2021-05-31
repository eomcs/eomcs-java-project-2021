package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 다른 서블릿을 실행하는 중에 오류가 발생했을 때 
// 포워딩 하는 서블릿이다.
// 이 서블릿의 주요 역할은 오류 내용을 출력하는 일이다.
//    
@SuppressWarnings("serial")
@WebServlet("/error")
public class ErrorHandler extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>실행 오류</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>요청 처리 중 오류 발생!</h1>");

    // web.xml에 예외처리를 설정한 경우,
    // 그 예외 객체는 서블릿 명세에 따라 정해진 이름으로 꺼내야 한다.
    // 속성 이름과 의미:
    // - javax.servlet.error.status_code : 에러 상태 코드
    // - javax.servlet.error.exception_type : 예외 클래스
    // - javax.servlet.error.message : 오류 메시지
    // - javax.servlet.error.request_uri : 예외가 발생한 URL
    // - javax.servlet.error.exception : 예외 객체
    // - javax.servlet.error.servlet_name : 예외가 발생한 서블릿 이름 
    // 
    Exception e = (Exception) request.getAttribute("javax.servlet.error.exception");
    if (e != null) {
      // 예외 객체에 간단한 메시지 정보가 들어 있다면 출력한다.
      if (e.getMessage() != null) {
        out.printf("<p>%s</p>\n", e.getMessage());
      }

      // 예외에 대한 상세 정보를 출력한다.
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);

      out.printf("<pre>%s</pre>\n", strWriter.toString());
    }

    out.println("</body>");
    out.println("</html>");
  }
}








