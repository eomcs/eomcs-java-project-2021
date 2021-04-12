# 26-a. 객체 생성을 자동화하기 : Java Proxy를 이용한 DAO 구현체 자동 생성


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
- com.eomcs.pms.dao.ProjectDao 인터페이스 변경
  - 메서드의 파라미터 개수가 여러 개일 경우 Map 타입으로 변경한다.
  - SqlSession의 `selectList()`, `selectOne()` 메서드와 사용 규칙을 맞추기 위함이다.
- com.eomcs.pms.service.impl.DefaultProjectService 클래스 변경
  - DAO 구현체의 메서드를 호출할 때 파라미터가 여러 개인 경우 맵 객체에 담아서 넘긴다.

### 4단계: DAO 인터페이스 구현체를 삭제한다.

- com.eomcs.pms.dao.mariadb 패키지 삭제

## 실습 결과
- src/main/java/com/eomcs/mybatis/MybatisDaoFactory.java 추가
- src/main/java/com/eomcs/mybatis/DaoWorker.java 추가
- src/main/resources/com/eomcs/pms/mapper/XxxMapper.xml 변경
- src/main/java/com/eomcs/pms/dao/ProjectDao.java 변경
- src/main/java/com/eomcs/pms/service/impl/DefaultProjectService.java 변경
- src/main/java/com/eomcs/pms/ClientApp.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb 패키지 삭제
