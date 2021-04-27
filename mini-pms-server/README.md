# 30-c. 웹 애플리케이션 서버 아키텍처로 전환하기 : `Command` 구현체를 `Servlet` 구현체로 바꾸기

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

### 3단계 - 게시글 조회 구현체를 `Servlet` 구현체로 변경하기

웹 브라우저가 웹 애플리케이션 서버에게 값을 보낼 때는 URL 뒤에 파라미터를 붙인다.
```
예) localhost:8080/pms/board/detail?no=100
```

서블릿에서 클라이언트가 보낸 값을 꺼낼 때는 다음과 같이 메서드를 호출한다.
```
예) int no = Integer.parseInt(request.getParameter("no"));
```

- com.eomcs.pms.web.BoardDetailHandler 추가
  - `com.eomcs.pms.handler.BoardDetialHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/board/detail?no=xxx`

### 4단계 - 로그인/로그인 사용자정보 조회/로그아웃 구현체를 `Servlet` 구현체로 변경하기

- com.eomcs.pms.web.LoginHandler 추가
  - `com.eomcs.pms.handler.LoginHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/login?email=aa@test.com&password=1111`
- com.eomcs.pms.web.UserInfoHandler 추가
  - `com.eomcs.pms.handler.UserInfoHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/userInfo`
- com.eomcs.pms.web.LogoutHandler 추가
  - `com.eomcs.pms.handler.LogoutHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/logout`

### 5단계 - 게시글 등록/변경/삭제 구현체를 `Servlet` 구현체로 변경하기

- com.eomcs.pms.web.BoardAddHandler 추가
  - `com.eomcs.pms.handler.BoardAddHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/board/add?title=xxx&content=xxxx`
- com.eomcs.pms.web.BoardUpdateHandler 추가
  - `com.eomcs.pms.handler.BoardUpdateHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/board/update?no=xxx&title=xxx&content=xxxx`
- com.eomcs.pms.web.BoardDeleteHandler 추가
  - `com.eomcs.pms.handler.BoardDeleteHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/board/delete?no=xxx`
- com.eomcs.pms.web.BoardSearchHandler 추가
  - `com.eomcs.pms.handler.BoardSearchHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/board/search?keyword=xxxx`

### 6단계 - 회원 관리 구현체를 `Servlet` 구현체로 변경하기

- com.eomcs.pms.web.MemberDetailHandler 추가
  - `com.eomcs.pms.handler.MemberDetialHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/member/detail?no=xxx`
- com.eomcs.pms.web.MemberAddHandler 추가
  - `com.eomcs.pms.handler.MemberAddHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/member/add?name=xxx&email=xxx&password=xxx&photo=xxx&tel=xxx`
- com.eomcs.pms.web.MemberUpdateHandler 추가
  - `com.eomcs.pms.handler.MemberUpdateHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/member/update?no=xxx&name=xxx&email=xxx&password=xxx&photo=xxx&tel=xxx`
- com.eomcs.pms.web.MemberDeleteHandler 추가
  - `com.eomcs.pms.handler.MemberDeleteHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/member/delete?no=xxx`

### 7단계 - 프로젝트 관리 구현체를 `Servlet` 구현체로 변경하기

- com.eomcs.pms.web.ProjectDetailHandler 추가
  - `com.eomcs.pms.handler.ProjectDetialHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/project/detail?no=xxx`
- com.eomcs.pms.web.ProjectAddHandler 추가
  - `com.eomcs.pms.handler.ProjectAddHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/project/add?title=xxx&content=xxx&startDate=xxx&endDate=xxx&member=xxx&member=xxx&member=xxx`
- com.eomcs.pms.web.ProjectUpdateHandler 추가
  - `com.eomcs.pms.handler.ProjectUpdateHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/project/update?no=xxx&title=xxx&content=xxx&startDate=xxx&endDate=xxx&member=xxx&member=xxx&member=xxx`
- com.eomcs.pms.web.ProjectDeleteHandler 추가
  - `com.eomcs.pms.handler.ProjectDeleteHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/project/delete?no=xxx`
- com.eomcs.pms.web.ProjectSearchHandler 추가
  - `com.eomcs.pms.handler.ProjectSearchHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/project/search?item=xxx&keyword=xxx`
- com.eomcs.pms.web.ProjectDetailSearchHandler 추가
  - `com.eomcs.pms.handler.ProjectDetailSearchHandler` 클래스를 가져와서 변경한다.
  - `src/main/resources/com/eomcs/pms/mapper/ProjectMapper.xml` 의 `findByKeywords` SQL을 변경한다.
  - HTTP 요청: `localhost:8080/pms/project/detailSearch?title=xxx&owner=xxx&member=xxx`
- com.eomcs.pms.web.ProjectMemberUpdateHandler 추가
  - `com.eomcs.pms.handler.ProjectMemberUpdateHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/project/memberUpdate?no=xxx`
- com.eomcs.pms.web.ProjectMemberDeleteHandler 추가
  - `com.eomcs.pms.handler.ProjectMemberDeleteHandler` 클래스를 가져와서 변경한다.
  - HTTP 요청: `localhost:8080/pms/project/memberDelete?no=xxx`


## 실습 결과
- src/main/java/com/eomcs/pms/web/AppInitHandler.java 추가
- src/main/java/com/eomcs/pms/web/BoardXxxHandler.java 추가
- src/main/java/com/eomcs/pms/web/MemberXxxHandler.java 추가
- src/main/java/com/eomcs/pms/web/ProjectXxxHandler.java 추가
- src/main/java/com/eomcs/pms/web/TaskXxxHandler.java 추가
- src/main/java/com/eomcs/pms/handler 패키지 삭제
