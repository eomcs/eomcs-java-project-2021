package com.eomcs.pms.table;

import java.io.DataOutputStream;
import java.util.List;

public interface DataTable {
  void service(String command, List<String> data, DataOutputStream out) throws Exception;
}
