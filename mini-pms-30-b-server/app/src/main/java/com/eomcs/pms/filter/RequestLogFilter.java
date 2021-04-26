package com.eomcs.pms.filter;

import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Filter;
import com.eomcs.util.FilterChain;

// 클라이언트 요청 정보를 기록하는 필터이다.
// => Command 구현체를 호출하기 전에 먼저 이 필터를 실행한다.
// => 누가? FilterChain 목록을 통해 체인을 따라가면서 실행할 것이다.
// => 필터는 Filter 규칙에 따라 만들어야 한다.
// 
@Component // ServerApp 에서 자동 생성할 객체를 찾을 때 이 애노테이션이 붙은 클래스를 찾는다.
public class RequestLogFilter implements Filter {
  @Override
  public void doFilter(CommandRequest request, CommandResponse response, FilterChain nextChain)
      throws Exception {

    // 다음 체인을 수행하기 전에 요청 정보를 출력한다.
    // => 클라이언트 요청에 대해 기록(log)을 남긴다.
    System.out.printf("[%s:%d] %s\n", 
        request.getRemoteAddr(), request.getRemotePort(), request.getCommandPath());

    // 이 필터에서 멈출게 아니라면 다음 체인을 실행시켜야 한다.
    // 만약 다음 체인을 실행하지 않으면 이 필터에서 ServerApp으로 리턴하게 된다.
    nextChain.doFilter(request, response);

  }
}








