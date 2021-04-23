package com.eomcs.util;

import com.eomcs.pms.handler.Command;

// FilterChain 구현체가 실행하는 필터다.
// => 이 필터는 Command 구현체를 실행한다.
// 
public class CommandFilter implements Filter {

  private Command command;

  public CommandFilter(Command command) {
    this.command = command;
  }

  @Override
  public void doFilter(CommandRequest request, CommandResponse response, FilterChain nextChain) throws Exception {
    // FilterChain 구현체가 이 필터의 메서드를 호출하면 
    // 다음과 같이 생성자에서 받아 둔 Command 구현체를 실행한다.

    command.service(request, response);

  }
}
