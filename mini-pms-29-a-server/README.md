# 29-a. `Chain of Responsibility` 디자인 패턴 : 프로젝트에 적용

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

### 1단계 - 커맨드 실행 전/후에 삽입될 필터의 호출 규칙을 정의한다.

- com.eomcs.util.FilterChain 인터페이스 생성
  - 필터 체인이 보관하고 있는 필터를 실행시킬 때 호출할 메서드의 규칙을 정의한다.
- com.eomcs.util.Filter 인터페이스 생성
  - 필터 체인이 보관할 객체가 반드시 구현해야 할 인터페이스이다.
  - 필터 체인이 호출하는 메서드이다.

### 2단계 - FilterChain 규칙에 따라 동작할 클래스를 정의한다.
- com.eomcs.util.DefaultFilterChain 클래스 생성
  - FilterChain 인터페이스를 구현한다.
  - 다음 체인의 주소를 보관한다.
  - 필터를 보관한다.

### 3단계 - 필터 목록을 관리할 클래스를 정의한다.

- com.eomcs.util.FilterList 클래스 생성
  - 필터 체인을 이용하여 필터 목록을 관리한다.

### 4단계 - `Command` 구현체를 실행하는 필터를 만들어 기본 필터로 등록한다.

- com.eomcs.util.CommandFilter 클래스 생성
  - 필터 체인이 이 필터를 실행하면, 이 필터에 탑재된 Command 구현체의 service()를 호출한다.

### 5단계 - `Command` 구현체를 실행할 때 `Filter`를 경유한다.

- com.eomcs.pms.ServerApp 클래스 변경
  - `Command` 구현체를 실행하는 `CommandFilter` 를 만들어 `FilterList` 에 보관한다.
  - `Command` 구현체를 실행할 때 `FilterList` 에 보관된 `FilterChain`을 통해 실행한다.


## 실습 결과
- src/main/java/com/eomcs/util/Filter.java 추가
- src/main/java/com/eomcs/util/FilterChain.java 추가
- src/main/java/com/eomcs/util/DefaultFilterChain.java 추가
- src/main/java/com/eomcs/util/FilterList.java 추가
- src/main/java/com/eomcs/util/CommandFilter.java 추가
- src/main/java/com/eomcs/pms/ServerApp.java 변경