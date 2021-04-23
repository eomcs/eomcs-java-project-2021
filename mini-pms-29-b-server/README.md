# 29-b. `Chain of Responsibility` 디자인 패턴 : 필터 객체 자동 로딩 및 실행

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

### 1단계 - `Filter` 를 이용하여 클라이언트의 접속 정보를 기록한다.

- 기존 방식
  - `ServerApp` 에 해당 기능의 코드를 직접 삽입해야 한다.
  - 즉 기능을 추가할 때마다 `ServerApp` 클래스를 변경해야 한다.
- 새 방식
  - 해당 기능을 수행하는 `Filter` 구현체를 만들어 삽입한다.
  - com.eomcs.pms.filter.RequestLogFilter 클래스 생성
    - 클라이언트 요청 정보를 서버 콘솔로 출력한다.
  - com.eomcs.pms.ServerApp 클래스 변경
    - `RequestLogFilter` 를 필터 목록에 등록한 후 실행한다.

### 2단계 - `Filter` 구현체를 자동으로 로딩하여 필터 목록에 등록한다.

- com.eomcs.pms.filter.RequestLogFilter 변경
  - 자동 생성하려면 `@Component` 애노테이션을 붙여야 한다.
- com.eomcs.pms.ServerApp 클래스 변경
  - `Command` 구현체를 찾을 때 `Filter` 구현체도 함께 찾아서 객체를 생성한다.
  - `registerCommands()`를 `registerCommandAndFilter()` 로 변경한다.
  - `isCommand()` 를 `isType()` 로 변경한다. 
  - `createCommand()` 를 `createObject()` 로 변경한다.
  - 자동 생성한 필터 객체를 저장할 컬렉션을 추가한다.
  - 필터 체인을 구성할 때마다 개발자가 만든 필터를 등록한다.


## 실습 결과
- src/main/java/com/eomcs/pms/RequestLogFilter.java 추가
- src/main/java/com/eomcs/pms/ServerApp.java 변경