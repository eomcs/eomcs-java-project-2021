# 31-e. Servlet 기술 활용하기 : 쿠키와 세션 활용

이번 훈련에서는,
- **쿠키(cookie)** 과 **세션(session)** 을 활용하는 방법을 배울 것이다.  

## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - 로그인 할 때 입력한 이메일을 쿠키를 사용해서 유지한다.

- com.eomcs.pms.web.LoginHandler 변경
  - 로그인 할 때 입력한 이메일 정보를 쿠키에 보관해 두었다가 다음에 로그인 입력폼을 출력할 때 자동 입력되게 한다.

### 2단계 - 프로젝트 입력을 3단계에 걸쳐 처리한다. 이때 페이지간 데이터 공유는 세션을 이용한다.

- com.eomcs.pms.web.ProjectAddHandler 변경
  - 3개의 클래스로 쪼개서 프로젝트를 정보를 입력 받는다.

 
## 실습 결과
- src/main/java/com/eomcs/pms/web/LoginHandler.java 변경
- src/main/java/com/eomcs/pms/web/ProjectAdd1Handler.java 추가
- src/main/java/com/eomcs/pms/web/ProjectAdd2Handler.java 추가
- src/main/java/com/eomcs/pms/web/ProjectAdd3Handler.java 추가
- src/main/java/com/eomcs/pms/web/ProjectAddHandler.java 변경
