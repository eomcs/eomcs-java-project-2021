package com.eomcs.util;

// 필터 체인의 목록을 관리한다.
//
public class FilterList {

  FilterChain header;

  public void add(Filter filter) {
    // - 필터를 담을 새 체인을 만든다.
    // - 새로 만든 체인에 필터를 담는다.
    // - 현재 가장 앞에 있는 체인의 주소를 담는다.
    // - 가장 앞 체인을 현재 체인으로 바꾼다.
    header = new DefaultFilterChain(header, filter);
  }

  public FilterChain getHeaderChain() {
    return header;
  }
}
