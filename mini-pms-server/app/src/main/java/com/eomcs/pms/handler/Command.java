package com.eomcs.pms.handler;

import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;

public interface Command {
  void service(CommandRequest request, CommandResponse response) throws Exception;
}
