package com.eomcs.pms.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// 프론트 컨트롤러는 페이지 컨트롤러와 페이지 컨트롤러가 의존하는 객체를 생성하기 위해
// 빈 컨테이너를 사용한다.
// 빈 컨테이너는 개발자가 지정한 설정에 맞춰 객체를 생성한다.
// 다음 클래스를 빈 컨테이너의 행동을 제어하는 클래스이다.
// 
// 제어하는 방법
// - 클래스 선언에 애노테이션을 붙여서 제어한다.
// - 클래스에 필드나 메서드를 추가하여 제어한다.
// 

//1) 빈 컨테이너가 자동으로 객체를 생성해야 하는 패키지를 등록한다.
@ComponentScan("com.eomcs.pms")

//2) Spring WebMVC 관련 객체를 찾아서 등록하는 기능을 활성화시킨다.
@EnableWebMvc

public class AppConfig {

}
