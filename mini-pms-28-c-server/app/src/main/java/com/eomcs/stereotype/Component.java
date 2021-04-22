package com.eomcs.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 이 애노테이션을 컴파일 한 후 생성된 클래스 파일(.class)에 포함할 것인지 여부를 지정한다.
// => RUNTIME : JVM을 실행하는 중에 애노테이션 정보를 조회할 수 있다.
@Retention(RetentionPolicy.RUNTIME)

// 이 애노테이션을 어디에 붙일 수 있는지 지정한다.
// => TYPE : 클래스, 인터페이스, Enum 선언부에 붙일 수 있다.
@Target(ElementType.TYPE)

// 자동으로 생성될 객체를 표시할 때 사용하는 특별한 주석
public @interface Component {

  // 이 애노테이션을 붙일 때 추가할 프로퍼티
  // => 프로퍼티 값을 설정하지 않으면 기본이 빈 문자열("")이다.
  String value() default "";
}
