# 20-c. `Iterator` 디자인 패턴 : Iterator 패턴에 인터페이스 문법 적용

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

- 데이터 목록을 조회하는 객체의 사용 규칙을 `Iterator` 인터페이스로 정의한다.
- `AbstractList` 추상 클래스가 `List` 인터페이스를 구현한다.
- XxxHandler에서 데이터 목록을 가리키는 의존 객체를 지정할 때 클래스 대신 인터페이스를 사용한다.

## 실습

### 1단계 - `AbstractIterator` 추상 클래스를 인터페이스로 전환한다.

- `Iterator` 인터페이스 생성
  - 이터레이터에서 제공해야 하는 메서드를 규칙으로 정의한다.
  - hasNext(), next() 메서드를 추상 메서드로 선언한다.

#### 작업 파일

- com.eomcs.util.Iterator 인터페이스 생성
- com.eomcs.util.AbstractIterator 클래스 삭제


### 2단계 - `XxxIterator` 클래스를 `Iterator` 인터페이스(규칙)에 따라 작성한다.

- `AbstractIterator` 추상 클래스를 상속 받는 대신에 `Iterator` 인터페이스를 구현한다.

#### 작업 파일

- com.eomcs.util.StackIterator 변경
- com.eomcs.util.QueueIterator 변경
- com.eomcs.util.ListIterator 변경


### 3단계 - `Iterator` 규칙에 따라 `StackIterator`, `QueueIterator`, `ListIterator` 를 사용하도록 기존 코드를 변경한다.

- `App` 클래스
  - `AbstractIterator` 대신 `Iterator` 를 사용한다.
  
#### 작업 파일

- com.eomcs.pms.App 클래스 변경

## 실습 결과

- src/main/java/com/eomcs/util/Iterator.java 추가
- src/main/java/com/eomcs/util/AbstractIterator.java 삭제
- src/main/java/com/eomcs/pms/App.java 변경