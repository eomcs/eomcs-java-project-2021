package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.util.Component;
import com.eomcs.util.PageController;

@Component("/logout")
public class LogoutHandler implements PageController {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.getSession().invalidate();
    return "redirect:login";
  }
}






