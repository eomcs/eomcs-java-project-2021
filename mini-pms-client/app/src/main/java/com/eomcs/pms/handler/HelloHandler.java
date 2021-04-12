package com.eomcs.pms.handler;

public class HelloHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("안녕하세요!");
  }

}
