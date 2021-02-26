package com.eomcs.pms.handler;

public class HelloHandler implements Command {

  @Override
  public void service() {
    System.out.println("안녕하세요!");
  }
}






