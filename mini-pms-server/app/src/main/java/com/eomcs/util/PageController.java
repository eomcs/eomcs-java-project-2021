package com.eomcs.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 페이지 컨트롤러의 구현 규칙
// - 이 규칙에 따라 프론트 컨트롤러가 호출할 것이다.
public interface PageController {
  String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
