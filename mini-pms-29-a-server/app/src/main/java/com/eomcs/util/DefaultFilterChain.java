package com.eomcs.util;

// FilterChain 규칙에 따라 구현한다.
// => 다음 체인의 주소를 보관해야 한다.
// => 실행할 필터를 보관해야 한다.
// 
public class DefaultFilterChain implements FilterChain {

  private FilterChain nextChain;
  private Filter filter;

  public DefaultFilterChain(FilterChain nextChain, Filter filter) {
    this.nextChain = nextChain;
    this.filter = filter;
  }

  @Override
  public void doFilter(CommandRequest request, CommandResponse response) throws Exception {
    // 개발자가 만든 기능을 실행한다.
    filter.doFilter(request, response, nextChain);
  }

  public FilterChain getNextChain() {
    return nextChain;
  }

  public void setNextChain(FilterChain nextChain) {
    this.nextChain = nextChain;
  }

  public Filter getFilter() {
    return filter;
  }

  public void setFilter(Filter filter) {
    this.filter = filter;
  }
}
