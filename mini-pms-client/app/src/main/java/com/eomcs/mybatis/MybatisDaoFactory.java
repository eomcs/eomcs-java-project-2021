package com.eomcs.mybatis;

import java.lang.reflect.Proxy;
import org.apache.ibatis.session.SqlSession;

public class MybatisDaoFactory {

  DaoWorker daoWorker;

  public MybatisDaoFactory(SqlSession sqlSession) {
    this.daoWorker = new DaoWorker(sqlSession);
  }

  // DAO 인터페이스를 구현한 객체를 만들어준다.
  @SuppressWarnings("unchecked")
  public <T>  T createDao(Class<T> daoInterface) {
    return (T) Proxy.newProxyInstance(
        this.getClass().getClassLoader(), // 자동 생성된 DAO 구현체를 메모리에 로딩할 클래스 로더
        new Class<?>[] {daoInterface}, // 자동 생성된 객체가 구현해야 할 인터페이스 목록 
        daoWorker // 실제 일을 할 객체 
        );
  }
}
