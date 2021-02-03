# 18-b. 다형성 활용 : 오버라이딩과 오버로딩 

이번 훈련에서는 **다형성(polymorphism)** 의 특징을 이용하는 사례를 다룰 것이다.

**다형성** 이란?
- 한 방식, 한 이름으로 다양한 타입의 데이터나 메서드를 다루는 기법이다.
- 같은 이름의 변수를 사용하여 여러 타입의 데이터를 다루는 것 : 다형적 변수(polymorphic variable) 
- 같은 이름의 메서드를 사용하여 여러 종류의 파라미터를 다루는 것 : 오버로딩(overloading)
  - 메서드를 호출할 때 전달하는 아규먼트에 따라 호출될 메서드가 결정된다.
- 부모 메서드와 같은 이름의 시그너처를 갖는 메서드를 정의하는 것 : 오버라이딩(overriding)
  - 메서드를 호출하는 객체의 타입에 따라 호출될 메서드가 결정된다.

다형성 문법을 잘 이용하면, 
- 한 개의 변수로 다양한 종류의 값을 다룰 수 있어 편리하다.
- 같은 기능을 하는 메서드에 대해 같은 이름을 사용할 수 있어 프로그래밍의 일관성을 유지할 수 있다.
- 상속 받은 메서드를 서브 클래스의 역할에 맞게 재정의 할 수 있어, 또한 프로그래밍의 일관성을 제공한다.

## 훈련 목표

- 다형적 변수(polymorphic variables)를 활용하여 다양한 타입의 객체를 다루는 방법을 배운다.
- 형변환을 연습한다.

## 훈련 내용

- 다형적 변수를 이용하여 Board, Member, Project, Task 타입의 객체를 모두 다룰 수 있는 ArrayList 클래스를 정의한다. 
- Board, Member, Project, Task 타입에 따라 개별적으로 만든 XxxList 클래스를 ArrayList로 교체한다.
- 원래 타입의 객체를 다룰 때는 형변환을 이용한다. 


## 실습

### 1단계 - 도메인 객체의 내용물이 같은지 비교하는 메서드를 재정의한다.

- `Board`, `Member`, `Project`, `Task` 클래스에서 `Object` 클래스로부터 상속 받은 equals(), hashCode() 메서드를 오버라이딩 한다.

#### 작업 파일

- com.eomcs.pms.domain.Board 클래스 변경
- com.eomcs.pms.domain.Member 클래스 변경
- com.eomcs.pms.domain.Project 클래스 변경
- com.eomcs.pms.domain.Task 클래스 변경

### 2단계 - `List` 에 객체 주소 삭제하는 delete()을 추가한다.

- `List` 클래스에 delete(Object) 메서드를 오버로딩 한다.
  
#### 작업 파일

- com.eomcs.util.List 클래스 변경

### 3단계 - XxxHandler 에서 항목을 삭제할 때 인덱스 대신 객체 주소를 이용한다.

- XxxHandler 클래스를 변경한다.
  - delete() 메서드 변경
- `List' 클래스에 indexOf(Object) 메서드를 정의한다.

#### 작업 파일

- com.eomcs.pms.handler.BoardHandler 클래스 변경
- com.eomcs.pms.handler.MemberHandler 클래스 변경
- com.eomcs.pms.handler.ProjectHandler 클래스 변경
- com.eomcs.pms.handler.TaskHandler 클래스 변경
- com.eomcs.util.List 클래스 변경


## 실습 결과

- src/main/java/com/eomcs/pms/domain/Board.java 변경
- src/main/java/com/eomcs/pms/domain/Member.java 변경
- src/main/java/com/eomcs/pms/domain/Project.java 변경
- src/main/java/com/eomcs/pms/domain/Task.java 변경
- src/main/java/com/eomcs/util/List.java 변경
- src/main/java/com/eomcs/pms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/pms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectHandler.java 변경
- src/main/java/com/eomcs/pms/handler/TaskHandler.java 변경