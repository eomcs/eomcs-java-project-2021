# 13-b. `Iterator` 디자인 패턴 : XxxIterator 에 대해 Generalization 수행 

**상속(inheritance)** 을 구현하는 방법에는 두 가지가 있다.

- **전문화(specialization)** 와 **일반화(generalization)** 이다.

**전문화** 는

- 기존 클래스의 기능을 그대로 활용할 수 있도록 연결하고
- 여기에 새 기능을 추가하거나 기존 기능을 변경하여 좀 더 특수 목적의 서브 클래스를 만드는 기법이다.
- 마치 부모로부터 무언가를 물려 받는 것과 같아서 **상속** 이라는 문법의 대표적인 기법으로 알려져 있다.
- 그래서 객체지향 프로그래밍의 상속을 얘기할 때는 대부분 **전문화** 를 가르키는 말이다.

**일반화** 는

- 클래스들의 공통 분모를 추출하여 수퍼 클래스를 정의하는 기법이다.
- 그리고 새로 정의한 수퍼 클래스와 부모/자식 관계를 맺는다.
- 프로그래밍 처음부터 상속을 고려하여 수퍼 클래스를 정의하는 것이 아니라
  코드를 리팩토링하는 과정에서 수퍼 클래스를 정의하는 것이기 때문에 초보 개발자에게 적합하다.
- 보통 일반화를 통해 추출된 수퍼 클래스는 서브 클래스에게 공통 분모를 상속해주는 것이 목적이다.
- 직접 인스턴스를 생성하고 사용하기 위해 만든 클래스가 아니다.
- 그래서 일반화를 통해 도출한 수퍼 클래스는 보통 추상 클래스로 정의한다.

이번 훈련에서는 이런 **상속(inheritance)** 의 기법 중에서 **일반화(generalization)** 기법을 연습할 것이다.

**추상 클래스** 는

- 서브 클래스에 기본 기능 및 공통 분모를 상속해 주는 역할을 하는 클래스다.
- new 명령을 통해 인스턴스를 생성할 수 없다.
- **상속** 의 기법 중에서 **일반화** 를 통해 수퍼 클래스를 정의한 경우 보통 추상 클래스로 선언한다.
- 추상 메서드를 가질 수 있다.

**추상 메서드** 는

- 서브 클래스에 따라 구현 방법이 다른 경우 보통 추상 메서드로 선언한다.
- 서브 클래스에서 반드시 구현해야 하는 메서드다.
- 즉 서브 클래스를 정의할 때 반드시 해당 메서드를 구현하도록 강제하고 싶다면 추상 메서드로 선언한다.
- 일반 클래스는 추상 메서드를 가질 수 없다.
- 추상 클래스와 인터페이스 만이 추상 메서드를 가질 수 있다.

이번 훈련에서는 **추상 클래스** 를 선언하고 **추상 메서드** 를 정의하는 것을 연습한다.

## 훈련 목표

- 상속의 기법에서 **전문화** 와 **일반화** 기법을 이해하고 구현하는 방법을 배운다.
- **추상 클래스** 의 용도를 이해하고 활용법을 연습한다.
- **다형적 변수(ploymorphic variables)** 를 이용하여 서브 클래스의 인스턴스를 다루는 것을 연습한다.
- **의존성 주입(dependency injection; DI)** 의 의미를 이해한다.
- **추상 클래스** 의 용도를 이해하고 활용법을 연습한다.
- **추상 메서드** 의 용도와 활용법을 연습한다.

## 훈련 내용

- `StackIterator`, `QueueIterator`, `ListIterator` 의 공통 분모를 추출하여 수퍼 클래스 `AbstractIterator` 를 정의한다.
- `AbstractIterator` 클래스를 추상 클래스로 변경한다.
- `AbstractIterator` 클래스의 메서드를 추상 메서드로 변경한다.
- `printCommandHistory()`의 파라미터 타입을  `AbstractIterator`로 변경한다.


### 주요 개념

- 일반화(Generalization)
  - 서브 클래스의 공통 분모를 추출하여 수퍼 클래스로 정의하고 상속 관계를 맺는 것.
- 다형적 변수(Polymorphic Variables)
  - Handler에서 사용할 목록 관리 객체를 수퍼 클래스의 레퍼런스로 선언하는 것.
  - 이를 통해 List의 서브 객체로 교체하기 쉽도록 만드는 것.
- 의존성 주입(DI; Dependency Injection)
  - Handler가 의존하는 객체를 내부에서 생성하지 않고 생성자를 통해 외부에서 주입 받는 것.
  - 이를 통해 의존 객체 교체가 쉽도록 만드는 것.

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
