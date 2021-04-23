# 29-c. `Chain of Responsibility` 디자인 패턴 : 로그인 검사를 필터로 분리

이번 훈련에서는,
- **Chain of Responsibility 디자인 패턴** 을 응용하는 방법을 배울 것이다.

**Chain of Responsibility 패턴** 은,
- 작업 요청을 받은 객체(sender)가 실제 작업자(receiver)에게 그 책임을 위임하는 구조에서 사용하는 설계 기법이다.
- 작업자 간에 연결 고리를 구축하여 작업을 나누어 처리할 수 있다.
- 체인 방식이기 때문에 작업에 참여하는 모든 객체가 서로 알 필요가 없다.
- 오직 자신과 연결된 다음 작업자만 알면 되기 때문에 객체 간에 결합도를 낮추는 효과가 있다.
- 런타임에서 연결 고리를 변경하거나 추가할 수 있어, 상황에 따라 실시간으로 기능을 추가하거나 삭제할 수 있다.
- 보통 필터링을 구현할 때 이 설계 기법을 많이 사용한다.

## 훈련 목표
- **Chain of Responsibility 패턴** 을 구현하는 것을 연습한다.
- **Chain of Responsibility 패턴** 을 적용한 후의 이점을 이해한다.

## 훈련 내용
- **Chain of Responsibility 패턴** 을 프로젝트에 적용한다.
- **Chain of Responsibility 패턴** 을 적용한 후에 새 기능을 추가하는 것을 체험한다.

## 실습

### 1단계 - 로그인 검사를 수행하는 필터를 만든다.

- com.eomcs.pms.filter.LoginCheckFilter 클래스 생성
  - 로그인 된 상태인지 검사한다.
  - 로그인 되었으면 다음 필터로 보낸다.
  - 로그인 되지 않았으면 안내 메시지를 보낸다.

### 2단계 - `Command` 구현체(XxxHandler)에서 로그인 여부를 검사하는 코드를 제거한다.

- com.eomcs.pms.handler.XxxHandler 변경
  - `BoardAddHandler`, `BoardUpdateHandler`, `BoardDeleteHandler` 
  - `ProjectAddHandler`, `ProjectUpdateHandler`, `ProjectDeleteHandler`, `ProjectMemberDeleteHandler`, `ProjectMemberUpdateHandler`

### 3단계 - 로그인 여부를 검사하는 필터의 동작을 확인한다.

- 실행 및 테스트

## 실습 결과
- src/main/java/com/eomcs/pms/filter/LoginCheckFilter.java 추가
- src/main/java/com/eomcs/pms/handler/BoardXxxHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectXxxHandler.java 변경