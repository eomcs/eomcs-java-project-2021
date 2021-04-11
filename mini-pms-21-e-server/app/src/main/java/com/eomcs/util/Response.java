package com.eomcs.util;

import java.util.ArrayList;
import java.util.List;

public class Response {
  private List<String> dataList = new ArrayList<>();

  public List<String> getDataList() {
    return dataList;
  }

  public void setDataList(List<String> dataList) {
    this.dataList = dataList;
  }

  public void appendData(String data) {
    dataList.add(data);
  }
}
