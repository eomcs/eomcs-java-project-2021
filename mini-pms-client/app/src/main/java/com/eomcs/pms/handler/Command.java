package com.eomcs.pms.handler;

import com.eomcs.driver.Statement;

public interface Command {
  void service(Statement stmt) throws Exception;
}
