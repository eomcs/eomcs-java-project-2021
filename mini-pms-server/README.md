# 34-a. Spring WebMVC 프레임워크 도입하기 : 스프링 WebMVC 적용


이번 훈련에서는,
- **Spring WebMVC 프레임워크** 를 사용하는 방법을 배울 것이다.

## 훈련 목표
- 
 
## 훈련 내용
-

## 실습 

### 1단계: 스프링 WebMVC 프레임워크 라이브러리 가져오기

- `search.maven.org` 에서 검색한다.
    - `spring-webmvc` 라이브러리
    - `spring-jdbc` 라이브러리
    - `mybatis-spring` 라이브러리
    - `log4j` 라이브러리
- `build.gradle` 파일에 의존 라이브러리 정보를 추가한다.
- `$ gradle eclipse` 실행한다.
- 이클립스 IDE에서 프로젝트를 갱신한다.

### 2단계: 스프링 WebMVC에서 제공하는 DispatcherServlet을 웹 애플리케이션에 설정한다.

- `web.xml` 파일에 프론트 컨트롤러 등록한다.
    - `DispatcherServlet` 클래스를 서블릿으로 등록한다.
    - 프론트 컨트롤러에서 사용할 빈 컨테이너의 클래스를 지정한다.
    - 빈 컨테이너의 설정을 처리할 클래스를 지정한다.
    - 멀티 파트 요청 파라미터 관련 설정을 추가한다.

### 3단계: 프론트 컨트롤러가 사용할 빈 컨테이너의 설정 클래스를 준비한다.

- com.eomcs.pms.config.AppConfig 클래스 생성 및 설정 

### 4단계: 페이지 컨트롤러를 Spring WebMVC 규칙에 따라 변경한다.

- com.eomcs.pms.web.XxxHandler 클래스 변경
    - @Controller 애노테이션을 붙인다.
    - 메서드에는 @RequestMapping 애노테이션을 붙인다.

### 5단계: 페이지 컨트롤러가 의존하는 객체에 대해 자동 생성시키기 위해 애노테이션으로 표시한다.

- com.eomcs.pms.service.impl.XxxService 클래스 변경
    - @Service 애노테이션을 붙인다.

### 6단계: 요청 파라미터의 문자 집합을 설정하는 필터를 스프링에서 제공하는 필터로 교체한다.

- web.xml 변경

## 실습 결과

