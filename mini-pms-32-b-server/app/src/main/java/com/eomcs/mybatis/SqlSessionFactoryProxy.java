package com.eomcs.mybatis;

import java.sql.Connection;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;

// SqlSessionFactory 구현체의 일부 기능을 바꾸는 역할을 한다.
public class SqlSessionFactoryProxy implements SqlSessionFactory {

  SqlSessionFactory original;

  // 스레드의 전용 보관소
  // => 이 보관소는 오직 SqlSession 객체만 담을 수 있다.
  // => 이 객체는 힙 메모리 영역의 인스턴스 필드로 생성되지 않는다.
  // => 각 스레드마다 보유하는 필드이다.
  ThreadLocal<SqlSessionProxy> threadLocal = new ThreadLocal<>();

  public SqlSessionFactoryProxy(SqlSessionFactory sqlSessionFactory) {
    this.original = sqlSessionFactory;
  }

  // 트랜잭션 관리자 전용 메서드
  // => 트랜잭션 관리자가 호출하는 메서드다.
  public void prepareSqlSession() {
    // 스레드가 작업하는 동안 사용할 SqlSession 객체를 미리 준비한다.
    // 생성한 SqlSession 객체는 스레드 전용 보관소에 담아 둔다.

    // 1) 수동 커밋으로 동작하는 SqlSessionProxy 객체를 만든다.
    SqlSessionProxy sqlSessionProxy = new SqlSessionProxy(original.openSession(false), true);

    // 2) 스레드 전용 보관소에 SqlSessionProxy 객체를 저장한다.
    threadLocal.set(sqlSessionProxy);
  }

  // 트랜잭션 관리자 전용 메서드
  // => 트랜잭션을 완료한 후에 더이상 SqlSession 을 사용할 일이 없다면 
  //    해당 스레드는 스레드 보관소에 저장된 SqlSession 객체를 제거해야 한다.
  // => 그래야 해당 스레드가 다음 작업을 처리할 때 초기 상태에서 작업을 수행할 수 있다. 
  public void closeSession() {
    // 1) 스레드 보관소에 들어 있는 SqlSessionProxy 객체를 꺼낸다.
    SqlSessionProxy sqlSessionProxy = threadLocal.get();
    if (sqlSessionProxy != null) {
      // 사용한 SqlSession 객체를 완전히 닫는다.
      // => 자원은 항상 쓰고 닫아야 한다.
      // => 특히 24시간 365일 내내 실행하는 서버인 경우 자원 해제는 메모리 관리 차원에서 아주 중요하다.
      sqlSessionProxy.realClose();
      threadLocal.remove();
    }
  }

  // 오리지널 객체의 기능 중에서 바꾸고자 하는 기능이 있다면 
  // 다음과 같이 프록시의 메서드에서 변경하라!
  @Override
  public SqlSession openSession(boolean autoCommit) {

    // 오토 커밋으로 동작하는 SqlSession 객체를 만들 때는 
    // 트랜잭션 제어와 상관없기 때문에 
    // 자동 커밋으로 동작하는 SqlSession 객체를 만들어 준다.
    if (autoCommit) {
      return original.openSession(autoCommit);
    }

    // 수동 커밋으로 동작하는 SqlSession 객체를 만들어 달라고 요청 받았다면,
    // 이건 고민해 봐야 한다.
    // 트랜잭션 관리자가 제어하기 위해 미리 SqlSession 객체를 만들어 스레드에 보관한 상태라면 
    // 그 객체를 꺼내서 줘야 한다.
    if (threadLocal.get() != null) {
      return threadLocal.get();
    }

    // 스레드 보관소에 저장된 SqlSession 객체가 없다는 것은 
    // 트랜잭션 관리자의 통제 없이 동작하는 SqlSession이 필요하다는 뜻이기 때문에
    // 자동 커밋으로 동작하는 SqlSession 객체를 만들어 주면 된다.
    return original.openSession(true);
  }

  @Override
  public SqlSession openSession() {
    return original.openSession();
  }

  @Override
  public SqlSession openSession(Connection connection) {
    return original.openSession(connection);
  }

  @Override
  public SqlSession openSession(TransactionIsolationLevel level) {
    return original.openSession(level);
  }

  @Override
  public SqlSession openSession(ExecutorType execType) {
    return original.openSession(execType);
  }

  @Override
  public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
    return original.openSession(execType, autoCommit);
  }

  @Override
  public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
    return original.openSession(execType, level);
  }

  @Override
  public SqlSession openSession(ExecutorType execType, Connection connection) {
    return original.openSession(execType, connection);
  }

  @Override
  public Configuration getConfiguration() {
    return original.getConfiguration();
  }


}
