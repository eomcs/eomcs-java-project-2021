package com.eomcs.pms.handler;

import com.eomcs.stereotype.Component;

@Component("/haha")
public class Hello2Handler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("오호라.. 안녕!!!");

  }

}
