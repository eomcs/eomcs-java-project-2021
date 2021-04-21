package com.eomcs.mybatis;

public class TransactionManager {

  SqlSessionFactoryProxy sqlSessionFactoryProxy;

  public TransactionManager(SqlSessionFactoryProxy sqlSessionFactoryProxy) {
    this.sqlSessionFactoryProxy = sqlSessionFactoryProxy;
  }

  public void beginTransaction() {
    // 트랜잭션을 시작하면, 
    // 트랜잭션 동안 사용할 SqlSession 객체를 준비하여 스레드에 보관해 둔다.
    sqlSessionFactoryProxy.prepareSqlSession();
  }

  public void commit() {
    // 트랜잭션 동안 수행한 모든 작업을 승인하려면 
    // 스레드 보관소에 저장된 SqlSessionProxy 객체를 꺼내서 commit()을 호출한다.
    SqlSessionProxy sqlSessionProxy = (SqlSessionProxy) sqlSessionFactoryProxy.openSession(false);
    sqlSessionProxy.commit();
    sqlSessionFactoryProxy.closeSession();
  }

  public void rollback() {
    // 트랜잭션 동안 수행한 모든 데이터 변경 작업을 취소한다.
    // 물론 스레드 보관소에 저장된 SqlSessionProxy 를 통해서 수행한다.
    SqlSessionProxy sqlSessionProxy = (SqlSessionProxy) sqlSessionFactoryProxy.openSession(false);
    sqlSessionProxy.rollback();
    sqlSessionFactoryProxy.closeSession();
  }

}
