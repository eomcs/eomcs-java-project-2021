# 31-a. Servlet 기술 활용하기 : 필터와 리스너 활용 및 초기화 파라미터 사용법

이번 훈련에서는,
- **서블릿** 컴포넌트 중에서 **필터와 리스너**를 활용하는 방법을 배울 것이다.  
- DD 파일에 설정한 초기화 파라미터 값을 다루는 방법을 배울 것이다.

## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - POST 요청으로 보낸 요청 파라미터에 대해 문자집합을 설정하는 필터를 만든다.

- com.eomcs.pms.web.filter.CharacterEncodingFilter 추가
  - `javax.servlet.Filter` 규칙에 따라 클래스를 정의한다.
  - 문자집합은 web.xml에 설정된 파라미터 값을 사용한다.

### 2단계 - AppInitHandler의 Mybatis 관련 설정 파일 경로를 서블릿 초기화 파라미터로 분리한다.

- com.eomcs.pms.web.AppInitHandler 변경
  - Mybatis 설정 파일의 경로를 web.xml에서 가져온다.

### 3단계 - 리스너를 사용하여 서블릿이 사용할 의존 객체를 준비한다.

- 기존에는 AppInitHandler 서블릿을 사용하여 웹 애플리케이션을 실행하는 동안 사용할 의존 객체를 준비하였다.
- 서블릿 보다 더 직관적인 리스너로 의존 객체를 준비해보자.
- com.eomcs.pms.web.listener.ContextLoaderListener 추가
  - `AppInitHandler`의 코드를 이 클래스로 복사한다.
- web.xml 변경
  - `AppInitHandler` 서블릿 배치 정보를 주석으로 만든다.
  - 이제부터 서블릿이 사용할 의존 객체는 `ContextLoaderListener` 가 준비할 것이다.

 
## 실습 결과
- src/main/java/com/eomcs/pms/web/filter/CharacterEncodingFilter.java 추가
- src/main/java/com/eomcs/pms/web/listener/ContextLoaderListener.java 추가
- src/main/java/com/eomcs/pms/web/AppInitHandler.java 변경
- src/main/webapp/WEB-INF/web.xml 변경
