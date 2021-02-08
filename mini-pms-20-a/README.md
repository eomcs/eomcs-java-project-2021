# 20-a. `Iterator` 디자인 패턴 : 데이터 조회 기능을 캡슐화

**반복자(Iterator) 패턴** 은 

- 객체 목록을 관리하는 컬렉션(collection)에서 
  목록 조회 기능을 별도의 객체로 캡슐화하는 설계 기법이다.
- 컬렉션의 관리 방식(data structure)에 상관없이 일관된 목록 조회 방법을 제공할 수 있다.
- 컬렉션을 변경하지 않고도 다양한 방식의 목록 조회 기법을 추가할 수 있다.


## 훈련 목표

- **반복자(Iterator) 패턴** 의 용도와 특징을 이해하고 구현하는 방법을 배운다.
- **인터페이스** 문법을 **반복자(Iterator) 패턴** 에 적용하는 방법을 배운다.
- **반복자(Iterator)** 를 활용하여 목록을 조회하는 방법을 배운다.


## 훈련 내용

- `Stack`, `Queue`, `List` 에서 목록을 조회하는 기능을 캡슐화하여 
  그 사용 규칙을 `Iterator` 인터페이스로 정의한다.
- `Stack`, `Queue`, `List` 각각에 대해 `Iterator` 규칙에 따라 **반복자** 를 구현한다.
- `Stack`, `Queue`, `List` 에서 값을 꺼낼 때 **반복자(`Iterator`)** 를 사용한다.

## 실습

### 1단계 - 스택을 조회하는 기능을 캡슐화 한다.

- `StackIterator` 클래스 생성
  - `java.util.Iterator` 인터페이스를 모방하여 메서드를 정의한다.

#### 작업 파일

- com.eomcs.util.StackIterator 클래스 생성


### 2단계 - `history` 명령을 처리할 때 `StackIterator` 객체를 사용한다.

- `App` 클래스 변경
  - `printCommandHistory()` 변경한다.

#### 작업 파일

- com.eomcs.pms.App 클래스 변경


### 3단계 - 큐를 조회하는 기능을 캡슐화 한다.

- `QueueIterator` 클래스 생성
  - `java.util.Iterator` 인터페이스를 모방하여 메서드를 정의한다.
- `App` 클래스 변경
  - `history2` 명령을 처리할 때 `QueueIterator` 객체를 사용한다.
  - `printCommandHistory2()` 변경한다.

#### 작업 파일

- com.eomcs.util.QueueIterator 클래스 생성
- com.eomcs.pms.App 클래스 변경


### 4단계 - 리스트를 조회하는 기능을 캡슐화 한다.

- `ListIterator` 클래스 생성
  - `java.util.Iterator` 인터페이스를 모방하여 메서드를 정의한다.
- `XxxHandler` 클래스 변경
  - `list()` 메서드에서 `ListIterator`를 사용하도록 변경한다.

#### 작업 파일

- com.eomcs.util.ListIterator 클래스 생성
- com.eomcs.pms.handler.BoardHandler 클래스 변경
- com.eomcs.pms.handler.MemberHandler 클래스 변경
- com.eomcs.pms.handler.ProjectHandler 클래스 변경
- com.eomcs.pms.handler.TaskHandler 클래스 변경
- com.eomcs.pms.App 클래스 변경



## 실습 결과

- src/main/java/com/eomcs/util/ListIterator.java 추가
- src/main/java/com/eomcs/util/StackIterator.java 추가
- src/main/java/com/eomcs/util/QueueIterator.java 추가
- src/main/java/com/eomcs/pms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/pms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectHandler.java 변경
- src/main/java/com/eomcs/pms/handler/TaskHandler.java 변경
- src/main/java/com/eomcs/pms/App.java 변경
  