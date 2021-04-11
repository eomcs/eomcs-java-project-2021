# 25-c. 비즈니스 로직 분리하기 : 서비스 객체를 인터페이스와 구현체로 분리

이번 훈련에서는,
- **인터페이스** 를 활용하여 객체의 교체를 쉽게 하는 방법을 배울 것이다.

**인터페이스** 는,
- 호출자와 피호출자 사이의 메서드 호출 규칙을 정의하는 문법이다.
- 객체 간의 결합도를 느슨하게 하여 객체 교체를 용이하게 만든다.
- 인터페이스 규칙에 따라 정의한 클래스를 *구현체(implementor)* 라 부른다.

## 훈련 목표
- *인터페이스* 문법의 용도의 활용을 연습한다.

## 훈련 내용
- 기존의 Service 클래스에서 메서드 호출 규칙을 추출하여 인터페이스로 정의한다.
- 커맨드 객체는 특정 클래스를 사용하는 대신에 인터페이스 구현체를 사용하게 만든다.

## 실습

### 1단계 - `BoardService` 객체를 인터페이스와 구현체로 분리한다.

- com.eomcs.pms.service.impl.DefaultBoardService 클래스 생성
  - 기존의 `BoardService` 클래스를 복사하여 `DefaultBoardService`를 생성한다.
- com.eomcs.pms.service.BoardService 인터페이스 생성
  - 기존의 `BoardService` 클래스를 인터페이스로 변경한다.
  - `DefaultBoardService` 클래스에 이 인터페이스를 선언한다.
- com.eomcs.pms.ClientApp 클래스 변경
  - `BoardService` 객체를 생성할 때 `DefaultBoardService` 클래스를 사용한다.

### 2단계 - `MemberService`, `ProjectService`, `TaskService` 객체도 `BoardService` 마찬가지로 인터페이스와 구현체로 분리한다.

화이팅!

## 실습 결과
- src/main/java/com/eomcs/pms/service/BoardService.java 변경
- src/main/java/com/eomcs/pms/service/MemberService.java 변경
- src/main/java/com/eomcs/pms/service/ProjectService.java 변경
- src/main/java/com/eomcs/pms/service/TaskService.java 변경
- src/main/java/com/eomcs/pms/service/impl/DefaultBoardService.java 생성
- src/main/java/com/eomcs/pms/service/impl/DefaultMemberService.java 생성
- src/main/java/com/eomcs/pms/service/impl/DefaultProjectService.java 생성
- src/main/java/com/eomcs/pms/service/impl/DefaultTaskService.java 생성
- src/main/java/com/eomcs/pms/ClientApp.java 변경
