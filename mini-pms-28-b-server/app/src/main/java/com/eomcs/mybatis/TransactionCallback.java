package com.eomcs.mybatis;

// TransactionTemplate이 작업 객체를 실행할 때, 
// 호출할 메서드의 규칙을 정의한 것.
//
public interface TransactionCallback {

  // 트랜잭션으로 다룰 작업은 다음 메서드로 구현해야 한다.
  Object doInTransaction() throws Exception;
}
