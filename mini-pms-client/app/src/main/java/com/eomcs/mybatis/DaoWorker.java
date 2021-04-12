package com.eomcs.mybatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DaoWorker implements InvocationHandler {

  @Override
  public Object invoke(Object daoProxy, Method method, Object[] args) throws Throwable {
    // Proxy 가 만들어준 DAO 구현체가 호출하는 메서드다.

    System.out.printf("%s.%s() 호출됨!\n", 
        daoProxy.getClass().getInterfaces()[0].getName(),
        method.getName());
    return null;
  }

}
