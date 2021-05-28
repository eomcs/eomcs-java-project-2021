# 33-b. Front Controller 도입하기 : 페이지 컨트롤러를 일반 객체로 전환하기


이번 훈련에서는,
- **Page Controller** 를 일반 객체로 다루는 방법을 배울 것이다.

## 훈련 목표
- 
 
## 훈련 내용
-

## 실습 

### 1단계: 페이지 컨트롤러 구현 규칙을 정의한다.

- com.eomcs.util.PageController 인터페이스 생성

### 2단계: 자동으로 생성할 객체를 표시할 때 사용할 애노테이션을 정의한다.

- com.eomcs.util.Component 애노테이션 생성

### 3단계: @Component 가 붙은 객체를 자동 생성한다.

- com.eomcs.pms.web.listener.ContextLoaderListener 변경

### 4단계: 페이지 컨트롤러를 일반 클래스로 전환한다.

- com.eomcs.pms.web.XxxHandler 변경

### 5단계: 프론트 컨트롤러를 변경한다.

- com.eomcs.pms.web.servlet.DispatcherServlet 변경


## 실습 결과

