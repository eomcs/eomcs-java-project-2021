# 28-a. 사용자 인증(authentication)하기 : 로그인/로그아웃 구현 + 세션 도입

이번 훈련에서는,
- **로그인/로그아웃** 을 다루는 방법을 연습한다.

## 훈련 목표
- **로그인/로그아웃** 의 구현을 연습한다.
- 커맨드 객체 간에 작업 결과를 공유하는 방법을 배운다.

## 훈련 내용
- 커맨드 객체 간에 작업 결과를 공유하기 위해 `Command` 인터페이스의 규칙을 변경한다.
- `Command` 의 변경에 맞춰 커맨드 구현체를 모두 변경한다.
- *로그인* , *로그아웃*, *로그인 사용자 정보 조회* 를 처리할 커맨드 클래스를 작성한다.
- *로그인* 에 필요한 데이터 처리 작업을 DAO에 추가한다.
- 게시글을 입력하거나 프로젝트를 입력할 때 로그인 정보를 사용한다.

## 실습

### 1단계 - 커맨드 객체 간의 작업 결과를 공유하기 위해 `Command` 인터페이스의 규칙을 변경한다.

- com.eomcs.pms.handler.Command 인터페이스 변경
  - `execute()` 에 context 맵 객체를 받는 파라미터를 추가한다.
- com.eomcs.pms.handler.XxxCommand 클래스 변경
  - `Command` 의 변경에 맞춰 메서드 시그너처(signature)를 변경한다.


### 1단계 - 로그인을 처리하는 `LoginHandler` 클래스를 작성한다.

다음과 같이 동작하도록 로그인을 구현한다.
```
명령> /login
[로그인]
이메일? 
입력> x1@test.com
암호? 
입력> 2222
사용자 정보가 맞지 않습니다.

명령> /login
[로그인]
이메일? 
입력> x1@test.com
암호? 
입력> 2222
x1 님 환영합니다.

명령> /login
로그인 되어 있습니다!

```

- com.eomcs.pms.handler.LoginHandler 생성
  - 사용자 이메일과 암호를 받아 인증을 수행한다.
  - 백업: LoginHandler01.java
- com.eomcs.pms.service.MemberService 변경
  - `get(email, password)` 메서드를 추가한다.
- com.eomcs.pms.service.impl.DefaultMemberService 변경
  - `get(email, password)` 메서드를 구현한다.  
- com.eomcs.pms.dao.MemberDao 인터페이스 변경  
  - `findByEmailPassword()` 메서드를 추가한다.
- src/main/resources/com/eomcs/pms/mapper/MemberMapper.xml 변경
  - `findByEmailPassword` select 문을 추가한다.

### 2단계 - 세션 객체를 이용하여 로그인 사용자 정보를 보관한다.

세션(Session) 
- 특정 기간 동안 사용하는 객체를 가리킨다.
  - 예) `로그인 중인 기간` 동안 사용하는 객체
  - 예) 한 작업을 여러 단계에 거치는 사용하는 객체


- com.eomcs.util.Session 추가
  - 클라이언트가 접속하는 동안 보관소로 사용할 객체를 정의한다.
- com.eomcs.uitl.CommandRequest 변경
  - 세션 객체를 저장할 필드를 추가하고 생성자를 변경한다.
  - 세터를 추가한다.
- com.eomcs.pms.ServerApp 변경
  - 접속한 클라이언트와 통신하는 동안 사용할 세션 객체를 준비한다.
  - 세션 객체를 `CommandRequest` 에 보관하여 `Command` 구현체의 `service()`를 호출할 때 전달한다.
- com.eomcs.pms.handler.LoginHandler 변경
  - 로그인 한 사용자의 정보를 다른 Command 구현체(핸들러)가 사용할 수 있도록 세션 객체에 보관한다.


### 3단계 - 로그인 사용자 정보를 조회한다.

다음과 같이 동작하도록 구현한다.
```
명령> /userInfo
사용자번호: 12
이름: aaa
이메일: aaa@test.com
사진: aaa.gif

명령> /userInfo
로그인 하지 않았습니다!
```

- com.eomcs.pms.handler.UserInfoHandler 생성
  - 현재 로그인 사용자 정보를 출력한다.

### 4단계 - 로그아웃을 구현한다.

다음과 같이 동작하도록 구현한다.
```
명령> /logout
aa 님 안녕히 가세요!

명령> /userInfo
로그인 하지 않았습니다!

명령> /logout
로그인 하지 않았습니다!

```

- com.eomcs.pms.handler.LogoutHandler 생성
  - 현재 세션을 초기화시킨다.

### 6단계 - 게시글 등록이나 변경, 삭제할 때 로그인 정보를 사용한다.

- com.eomcs.pms.handler.BoardAddHandler 변경
  - 게시글 작성자 정보를 입력 받지 않고 로그인 정보를 사용하도록 변경한다.
```
명령> /board/add
[게시물 등록]
제목? test
내용? okok
게시글을 등록하였습니다.

명령> /board/add
[게시물 등록]
로그인 하지 않았습니다!
```

- com.eomcs.pms.handler.BoardUpdateHandler 변경
  - 로그인 사용자가 게시글을 작성한 사용자일 때만 게시글을 변경할 수 있다.
```
명령> /board/update
[게시물 변경]
번호?
입력> 20
제목(haha)? 
입력> test
내용(hoho)? 
입력> okok
게시글을 변경하였습니다.

명령> /board/update
[게시물 변경]
번호?
입력> 20
변경 권한이 없습니다!

명령> /board/update
[게시물 변경]
로그인 하지 않았습니다!
```

- com.eomcs.pms.handler.BoardDeleteHandler 변경
  - 로그인 사용자가 게시글 작성자일 때만 게시글을 삭제할 수 있다.
```
명령> /board/delete
[게시물 삭제]
번호? 
입력> 20
정말 삭제하시겠습니까?(y/N) y
게시글을 삭제하였습니다.

명령> /board/delete
[게시물 삭제]
번호? 
입력> 20
삭제 권한이 없습니다!

명령> /board/delete
[게시물 삭제]
로그인 하지 않았습니다!
```

### 7단계 - 프로젝트를 등록, 변경, 삭제할 때 로그인 정보를 사용한다.

- com.eomcs.pms.handler.ProjectAddHandler 변경
  - 프로젝트 정보를 등록할 때 관리자는 로그인 사용자로 지정한다.
```
명령> /project/add
[프로젝트 등록]
프로젝트명? projectA
내용? test..ok
시작일? 2020-1-1
종료일? 2020-2-2
팀원?(완료: 빈 문자열) bbb
팀원?(완료: 빈 문자열) ccc
팀원?(완료: 빈 문자열) x1
팀원?(완료: 빈 문자열)

명령>
```

- com.eomcs.pms.handler.ProjectUpdateHandler 변경
  - 프로젝트 정보를 변경할 때 관리자는 변경하지 않는다.
```
명령> /project/update
[프로젝트 변경]
번호? 5
프로젝트명(xxx)? okok
내용(xxxxxxxx)? nono
시작일(2020-02-02)? 2020-3-3
종료일(2020-03-03)? 2020-4-4
팀원?(완료: 빈 문자열) aaa
팀원?(완료: 빈 문자열) bbb
팀원?(완료: 빈 문자열) ddd
팀원?(완료: 빈 문자열)
정말 변경하시겠습니까?(y/N) y
프로젝트를 변경하였습니다.

명령>
```

## 실습 결과
- src/main/java/com/eomcs/pms/handler/Command.java 변경
- src/main/java/com/eomcs/pms/handler/XxxCommand.java 변경
- src/main/java/com/eomcs/pms/dao/MemberDao.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/MemberDaoImpl.java 변경
- src/main/java/com/eomcs/pms/handler/LoginCommand.java 생성
- src/main/java/com/eomcs/pms/handler/WhoamiCommand.java 생성
- src/main/java/com/eomcs/pms/handler/LogoutCommand.java 생성
- src/main/java/com/eomcs/pms/handler/BoardAddCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectAddCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectUpdateCommand.java 변경
- src/main/java/com/eomcs/pms/App.java 변경
