package com.eomcs.util;

import java.io.PrintWriter;

// 클라이언트에게 응답하는 것과 관련된 일을 하는 객체
public class CommandResponse {
  private PrintWriter writer;

  public CommandResponse(PrintWriter writer) {
    this.writer = writer;
  }

  public PrintWriter getWriter() {
    return this.writer;
  }

}
