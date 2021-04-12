# 34-a. 객체 생성을 자동화하기 : Java Proxy를 이용한 DAO 구현체 자동 생성


## 훈련 목표
- Java Proxy의 구동 원리를 이해한다.
- Java Proxy를 이용하여 인터페이스 구현체를 자동으로 생성할 수 있다.

## 훈련 내용


## 실습

### 1단계: 복잡한 DAO 생성을 단순화시키는 팩토리 클래스를 정의한다.

- com.eomcs.mybatis.MybatisDaoFactory 클래스 추가
  - DAO 프록시 객체를 생성하는 팩토리 메서드 createDao()를 정의한다.
  - 인터페이스에 따라 리턴 타입을 다르도록 제네릭을 적용한다.

### 2단계: MybatisDaoFactory를 사용하여 DAO 구현체를 만든다.

- com.eomcs.ClientApp 클래스 변경
  - DAO 구현체를 생성하는 `createDao()` 메서드를 구체적으로 구현한다.
- com.eomcs.mybatis.DaoWorker 클래스 추가
  - DAO 프록시 객체가 사용할 수 있도록 InvocationHandler 규칙에 따라 작성한다.

### 3단계: InvocationHandler에서 SQL을 찾기 쉽도록 DAO 인터페이스 메서드명과 SQL ID를 일치시킨다.

- src/main/resources/com/eomcs/pms/mapper/XxxMapper.xml 변경
  - namespace 값을 인터페이스 전체 이름(fully-qualified name)과 일치시킨다.
  - 메서드에서 사용할 SQL은 메서드 이름과 일치시킨다.



### 3단계: MybatisDaoFactory 클래스를 구현한다.

- com.eomcs.mybatis.MybatisDaoFactory 클래스 추가
  - InvocationHandler 구현체를 람다 문법을 사용하여 로컬 클래스로 정의한다. 





### 3단계: DAO 객체 생성에 프록시 생성기를 적용한다.

- com.eomcs.lms.dao.* 에서 DAO 구현체 모두 제거
- com.eomcs.lms.DataLoaderListener 변경
  - MybatisDaoFactory를 이용하여 DAO 구현 객체 생성한다.

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
