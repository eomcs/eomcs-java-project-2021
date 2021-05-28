package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectAdd1Handler {

  @RequestMapping("/project/add1")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/jsp/project/form1.jsp";
  }
}








