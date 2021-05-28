package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectAdd2Handler {

  @RequestMapping("/project/add2")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // 클라이언트에서 보낸 값을 세션에 보관한다.
    HttpSession session = request.getSession();
    session.setAttribute("title", request.getParameter("title"));
    return "/jsp/project/form2.jsp";
  }
}








