package com.eomcs.mybatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public class DaoWorker implements InvocationHandler {

  SqlSession sqlSession;

  public DaoWorker(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  @Override
  public Object invoke(Object daoProxy, Method method, Object[] args) throws Throwable {
    // Proxy 가 만들어준 DAO 구현체가 호출하는 메서드다.

    // 1) SqlSession의 메서드를 호출할 때 넘겨 줄 SQL ID를 준비한다.
    // => SQL ID는 인터페이스의 fully-qualified name 과 같다고 가정하자.
    String sqlId = daoProxy.getClass().getInterfaces()[0].getName() + "." + method.getName();

    // 2) SqlSession의 메서드를 호출할 때 넘겨 줄 파라미터를 준비한다.'
    Object param = (args == null) ? null : args[0];

    // 3) 메서드의 리턴 타입에 따라 적절한 SqlSession의 메서드를 호출한다.
    if (method.getReturnType() == int.class ||
        method.getReturnType() == void.class) {
      return sqlSession.insert(sqlId, param);

    } else if (method.getReturnType() == List.class) {
      return sqlSession.selectList(sqlId, param);

    } else {
      return sqlSession.selectOne(sqlId, param);
    }
  }
}
