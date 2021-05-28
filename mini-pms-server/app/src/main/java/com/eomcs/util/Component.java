package com.eomcs.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 자동으로 생성할 객체를 표시할 때 이 애노테이션을 붙인다.
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
  String value() default "";
}
