package com.eomcs.util;

public interface ObjectFactory<T> {
  T create(String csvStr);
}
