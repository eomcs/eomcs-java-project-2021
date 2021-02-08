# 20-b. `Iterator` 디자인 패턴 : XxxIterator 에 대해 Generalization 수행 

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

### 1단계 - `StackIterator`, `QueueIterator`, `ListIterator`의 공통점을 추출하여 수퍼 클래스로 정의한다.

- `AbstractIterator` 클래스 생성
  - 이터레이터들에 대해 generalization 을 수행하여 수퍼 클래스를 정의한다.
  - 기존의 이터레이터들은 이 수퍼 클래스를 상속 받도록 변경한다.
  - 추상 클래스로 선언
    - 서브 클래스의 공통 분모를 정의하는 목적이기 때문에 직접 인스턴스를 생성하지 못하도록 만든다.
  - 추상 메서드 선언
    - 자료 구조에 따라 조회 방법이 다를 것이기 때문에 수퍼 클래스에서 메서드를 정의할 필요가 없다.
    - hasNext(), next() 메서드를 추상 메서드로 선언한다.

#### 작업 파일

- com.eomcs.util.AbstractIterator 클래스 생성


### 2단계 - `StackIterator`, `QueueIterator`, `ListIterator`가 `AbstractIterator`를 상속 받는다.

- `XxxIterator` 클래스 변경
  - `AbstractIterator` 클래스를 상속 받는다.

#### 작업 파일

- com.eomcs.util.ListIterator 클래스 변경
- com.eomcs.util.StackIterator 클래스 변경 
- com.eomcs.util.QueueIterator 클래스 변경


### 3단계 - `StackIterator`와 `QueueIterator`를 한 타입으로 다룬다.

- `App` 클래스 변경
  - `printCommandHistory()` 메서드에서 `history`와 `history2` 명령을 모두 처리한다.

#### 작업 파일

- com.eomcs.pms.App 클래스 변경
  - `printCommandHistory()` 변경
  - `printCommandHistory2()` 삭제


## 실습 결과

- src/main/java/com/eomcs/util/AbstractIterator.java 추가
- src/main/java/com/eomcs/util/ListIterator.java 변경
- src/main/java/com/eomcs/util/StackIterator.java 변경
- src/main/java/com/eomcs/util/QueueIterator.java 변경
- src/main/java/com/eomcs/pms/App.java 변경
  