package com.eomcs.mybatis;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

// SqlSessionFactory 객체가 만들어 주는 SqlSession 의 일부 기능을 바꾸고 싶다.
// 그렇다고 기존의 오리지널 코드를 변경할 수는 없다.
// 그럼 해결책은? 
// 다음 클래스처럼 기존 클래스의 기능을 그대로 흉내 내면서 
// 오리지널 객체의 일부 기능만 변경하도록 프록시 객체를 만들면 된다.
//
public class SqlSessionProxy implements SqlSession {

  SqlSession original;
  boolean isInTransaction;

  public SqlSessionProxy(SqlSession sqlSession, boolean transaction) {
    this.original = sqlSession;
    this.isInTransaction = transaction;
  }

  // 트랜잭션을 완료한 상태일 때  
  // SqlSession 객체를 완전히 자원 해제하기 위해
  // 트랜잭션 관리자가 다음 메서드를 호출할 것이다.
  public void realClose() {
    original.close();
  }

  // '자원을 해제하라' 한다해서 무조건 해제하지 말라!
  // 트랜잭션에서 사용하는 경우에는 해제하면 안된다.
  // 트랜잭션에서 사용하는 경우가 아닐 때만 닫아야 한다.
  @Override
  public void close() {
    if (isInTransaction) {
      return; // 트랜잭션에 포함된 상태일 때는 자원을 해제하지 않는다.
    }
    original.close();
  }


  @Override
  public <T> T selectOne(String statement) {
    return original.selectOne(statement);
  }

  @Override
  public <T> T selectOne(String statement, Object parameter) {
    return original.selectOne(statement, parameter);
  }

  @Override
  public <E> List<E> selectList(String statement) {
    return original.selectList(statement);
  }

  @Override
  public <E> List<E> selectList(String statement, Object parameter) {
    return original.selectList(statement, parameter);
  }

  @Override
  public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
    return original.selectList(statement, parameter, rowBounds);
  }

  @Override
  public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
    return original.selectMap(statement, mapKey);
  }

  @Override
  public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
    return original.selectMap(statement, parameter, mapKey);
  }

  @Override
  public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey,
      RowBounds rowBounds) {
    return original.selectMap(statement, parameter, mapKey, rowBounds);
  }

  @Override
  public <T> Cursor<T> selectCursor(String statement) {
    return original.selectCursor(statement);
  }

  @Override
  public <T> Cursor<T> selectCursor(String statement, Object parameter) {
    return original.selectCursor(statement, parameter);
  }

  @Override
  public <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds) {
    return original.selectCursor(statement, parameter, rowBounds);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void select(String statement, Object parameter, ResultHandler handler) {
    original.select(statement, parameter, handler);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void select(String statement, ResultHandler handler) {
    original.select(statement, handler);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void select(String statement, Object parameter, RowBounds rowBounds,
      ResultHandler handler) {
    original.select(statement, parameter, rowBounds, handler);
  }

  @Override
  public int insert(String statement) {
    return original.insert(statement);
  }

  @Override
  public int insert(String statement, Object parameter) {
    return original.insert(statement, parameter);
  }

  @Override
  public int update(String statement) {
    return original.update(statement);
  }

  @Override
  public int update(String statement, Object parameter) {
    return original.update(statement, parameter);
  }

  @Override
  public int delete(String statement) {
    return original.delete(statement);
  }

  @Override
  public int delete(String statement, Object parameter) {
    return original.delete(statement, parameter);
  }

  @Override
  public void commit() {
    original.commit();
  }

  @Override
  public void commit(boolean force) {
    original.commit(force);
  }

  @Override
  public void rollback() {
    original.rollback();
  }

  @Override
  public void rollback(boolean force) {
    original.rollback(force);
  }

  @Override
  public List<BatchResult> flushStatements() {
    return original.flushStatements();
  }


  @Override
  public void clearCache() {
    original.clearCache();
  }

  @Override
  public Configuration getConfiguration() {
    return original.getConfiguration();
  }

  @Override
  public <T> T getMapper(Class<T> type) {
    return original.getMapper(type);
  }

  @Override
  public Connection getConnection() {
    return original.getConnection();
  }


}
