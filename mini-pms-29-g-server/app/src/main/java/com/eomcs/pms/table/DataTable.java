package com.eomcs.pms.table;

import com.eomcs.util.Request;
import com.eomcs.util.Response;

public interface DataTable {
  void service(Request request, Response response) throws Exception;
}
