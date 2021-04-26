# 30-b. 웹 애플리케이션 서버 아키텍처로 전환하기 : `Command` 구현체를 `Servlet` 구현체로 바꾸기

이번 훈련에서는,
- **JavaEE** 의 **Servlet/JSP** 기술을 이용하여 기존의 애플리케이션 서버 아키텍처를 웹 애플리케이션 서버(Web Application Server: WAS) 아키텍처로 전환할 것이다.  

## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - 서블릿들이 사용할 의존 객체를 준비한다.

- com.eomcs.pms.web.AppInitHandler 추가 
  - `javax.servlet.Servlet` 인터페이스를 구현한다.
  - `init()` 메서드에 `ServerApp` 클래스에 있던 서비스/DAO/Mybatis/트랜잭션 객체 생성 코드를 가져온다.
  - 생성한 객체는 `ServletContext` 에 보관해 둔다.

### 2단계 - 게시글/회원/프로젝트 목록 조회 구현체를 `Servlet` 구현체로 변경하기

- com.eomcs.pms.web.BoardListHandler 추가
  - `com.eomcs.pms.handler.BoardListHandler` 클래스를 가져와서 변경한다.
- com.eomcs.pms.web.MemberListHandler 추가
  - `com.eomcs.pms.handler.MemberListHandler` 클래스를 가져와서 변경한다.
- com.eomcs.pms.web.ProjectListHandler 추가
  - `com.eomcs.pms.handler.ProjectListHandler` 클래스를 가져와서 변경한다.

### 3단계 - 게시글 조회/등록/변경/삭제 구현체를 `Servlet` 구현체로 변경하기

웹 애플리케이션 서버에게 값을 보낼 때는 URL 뒤에 파라미터 값을 붙인다.
```
예) localhost:8080/pms/board/detail?no=100
```

서블릿에서 클라이언트가 보낸 값을 꺼낼 때는 다음과 같이 메서드를 호출한다.
```
예) int no = Integer.parseInt(request.getParameter("no"));
```

- com.eomcs.pms.web.BoardDetailHandler 추가
  - `com.eomcs.pms.handler.BoardDetialHandler` 클래스를 가져와서 변경한다.
- com.eomcs.pms.web.BoardAddHandler 추가
  - `com.eomcs.pms.handler.BoardAddHandler` 클래스를 가져와서 변경한다.


## 실습 결과
- src/main/java/com/eomcs/pms/web/AppInitHandler.java 추가
- src/main/java/com/eomcs/pms/web/BoardListHandler.java 추가
- src/main/java/com/eomcs/pms/web/MemberListHandler.java 추가
- src/main/java/com/eomcs/pms/web/ProjectListHandler.java 추가

