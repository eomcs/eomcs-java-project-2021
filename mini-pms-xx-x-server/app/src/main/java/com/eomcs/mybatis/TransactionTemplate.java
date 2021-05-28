package com.eomcs.mybatis;

// 트랜잭션 제어 코드를 캡슐화 한다.
//
public class TransactionTemplate {

  private TransactionManager txManager;

  public TransactionTemplate(TransactionManager txManager) {
    this.txManager = txManager;
  }

  public Object execute(TransactionCallback cb) throws Exception {
    txManager.beginTransaction();

    try {
      Object rt = cb.doInTransaction();
      txManager.commit();
      return rt;

    } catch (Exception e) {
      txManager.rollback();
      throw e;
    }
  }
}
