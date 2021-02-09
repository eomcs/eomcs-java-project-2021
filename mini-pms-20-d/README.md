# 20-d. `Iterator` 디자인 패턴 : GRASP의 Creator 패턴 적용

**인터페이스** 는 

- 객체 간의 사용 규칙을 정의할 때 사용하는 문법이다.
- 즉 호출할 때 어떤 이름의 메서드를 어떤 파라미터로 호출해야 하는 지 정의한 것이다.
- 구체적인 동작은 클래스가 구현한다.
- 호출자와 피호출자 간의 직접적인 종속성을 제거할 수 있어 유지보수에 좋다. 

**인터페이스* 에 대한 **추상 클래스** 의 활용

- 인터페이스에 정의된 규칙이 많을 경우, 그 많은 메서드를 직접 구현하는 일은 번거롭다.
- 그래서 **추상 클래스** 를 통해 일부 메서드를 구현하고,
  나머지는 서브 클래스에게 맡기는 프로그래밍 기법을 많이 사용한다. 

이번 훈련에서는 **인터페이스** 를 활용하여 객체 사용 규칙을 정의하는 것과,
**추상 클래스** 를 활용하여 인터페이스의 일부 구현을 처리하는 방법을 연습할 것이다.
**인터페이스** 와 **추상 클래스** 의 *콜라보(collaboration)*를 경험해보자.

## 훈련 목표

- **인터페이스** 의 용도와 특징을 이해한다.
- **인터페이스** 를 정의하고 활용하는 방법을 연습한다.
- **추상 클래스** 를 통해 인터페이스의 구현을 보조하는 방법을 배운다.


## 훈련 내용

- 데이터 목록을 다루는 객체의 사용 규칙을 `List` 인터페이스로 정의한다.
- `AbstractList` 추상 클래스가 `List` 인터페이스를 구현한다.
- XxxHandler에서 데이터 목록을 가리키는 의존 객체를 지정할 때 클래스 대신 인터페이스를 사용한다.

## 실습

### 1단계 - `StackIterator` 생성 책임을 `App`에서 `Stack` 클래스로 옮긴다.

- `Stack` 클래스 변경
  - iterator() 메서드 정의
  - 이 메서드에서 StackIterator 인스턴스를 만들어 리턴한다.

#### 작업 파일

- com.eomcs.util.Stack 클래스 변경
- com.eomcs.pms.App 클래스 변경


### 2단계 - `QueueIterator` 생성 책임을 `App`에서 `Queue` 클래스로 옮긴다.

- `Queue` 클래스 변경
  - iterator() 메서드 정의
  - 이 메서드에서 QueueIterator 인스턴스를 만들어 리턴한다.

#### 작업 파일

- com.eomcs.util.Queue 클래스 변경
- com.eomcs.pms.App 클래스 변경

### 3단계 - `ListIterator` 생성 책임을 `XxxHandler`에서 `List` 클래스로 옮긴다.

- `List` 클래스 변경
  - iterator() 메서드 정의
  - 이 메서드에서 ListIterator 인스턴스를 만들어 리턴한다.

#### 작업 파일

- com.eomcs.util.List 클래스 변경
- com.eomcs.pms.handler.BoardHandler 클래스 변경
- com.eomcs.pms.handler.MemberHandler 클래스 변경
- com.eomcs.pms.handler.ProjectHandler 클래스 변경
- com.eomcs.pms.handler.TaskHandler 클래스 변경


## 실습 결과

- src/main/java/com/eomcs/util/Stack.java 변경
- src/main/java/com/eomcs/util/Queue.java 변경
- src/main/java/com/eomcs/util/List.java 변경
- src/main/java/com/eomcs/pms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/pms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectHandler.java 변경
- src/main/java/com/eomcs/pms/handler/TaskHandler.java 변경
- src/main/java/com/eomcs/pms/App.java 변경