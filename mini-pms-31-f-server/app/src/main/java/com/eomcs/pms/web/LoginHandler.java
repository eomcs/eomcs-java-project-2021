package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
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
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>로그인</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>로그인</h1>");

    out.println("<form method='post'>");
    out.println("<table border='1'>");
    out.println("<tbody>");
    out.printf("<tr><th>이메일</th>"
        + "<td><input name='email' type='email' value='%s'></td></tr>\n", email);
    out.println("<tr><th>암호</th><td><input name='password' type='password'></td></tr>");
    out.println("</tbody>");

    out.println("<tfoot>");
    out.println("<tr><td colspan='2'><input type='checkbox' name='saveEmail'>이메일 저장</td></tr>");
    out.println("<tr><td colspan='2'><button>로그인</button></td></tr>");
    out.println("</tfoot>");
    out.println("</table>");
    out.println("</form>");

    out.println("</body>");
    out.println("</html>");

  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>로그인</title>");

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
      if (member == null) {
        // 로그인 실패한다면 세션 객체의 모든 내용을 삭제한다.
        request.getSession().invalidate(); 
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>로그인 결과</h1>");
        out.println("<p>사용자 정보가 맞지 않습니다.</p>");

        response.sendRedirect("login");

        //        HTTP/1.1 302 
        //        Set-Cookie: JSESSIONID=B013592181013C18D8AF39EA27D74B83; Path=/pms; HttpOnly
        //        Location: login
        //        Content-Type: text/html;charset=UTF-8
        //        Content-Length: 0
        //        Date: Wed, 12 May 2021 07:40:32 GMT
        //        Keep-Alive: timeout=20
        //        Proxy-Connection: keep-alive

        // 로그인을 요청한 후 응답 결과를 보면 위와 같다. 
        // 여기서 주목해야 할 사항은 응답에 println() 으로 출력한 결과가 없다는 것이다.
        // 왜?
        // - sendRedirect()를 호출하면 버퍼에 들어 있는 출력 내용을 버린다.
        // - 응답할 때 콘텐트를 보내기 않기 때문이다.
        // - 따라서 리다이렉트를 할 생각이라면 콘텐트를 출력하지 말라!!!

      } else {
        // 로그인 성공한다면, 로그인 사용자 정보를 세션 객체에 보관한다.
        request.getSession().setAttribute("loginUser", member);      

        out.println("</head>");
        out.println("<body>");
        out.println("<h1>로그인 결과</h1>");
        out.printf("<p>%s 님 환영합니다.</p>\n", member.getName());

        response.sendRedirect("userInfo");
      }


    } catch (Exception e) {
      throw new ServletException(e);
    }

    out.println("</body>");
    out.println("</html>");
  }
}






