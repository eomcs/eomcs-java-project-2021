# 27-e. 애플리케이션을 클라이언트/서버 구조로 변경하기 : 멀티스레드 환경에서 트랜잭션 제어

이번 훈련에서는,
- *멀티 스레드* 환경에서 *Mybatis* 의 *트랜잭션* 을 다루는 방법을 배울 것이다.
- 기존 클래스의 코드를 손대지 않고 일부 기능을 변경하는 **프록시 패턴** 설계 기법을 배운다.

**프록시(proxy) 디자인 패턴** 은,


## 훈련 목표
- `SqlSession` 객체를 통해 트랜잭션을 다루는 방법을 연습한다.
- **프록시 패턴** 의 용도와 동작 원리를 이해한다.
- **프록시 패턴** 을 적용하는 방법을 연습한다.

## 훈련 내용
- 프로젝트의 상세 정보를 출력할 때 작업 목록을 추가한다.
- DAO의 메서드에서 트랜잭션을 다룰 때 문제가 되는 상황을 경험한다.
- 트랜잭션의 통제를 *DAO* 대신 *Command* 객체가 수행한다.
- `SqlSession` 객체의 트랜잭션 통제를 위해 프록시 패턴을 적용한다.

## 실습

### 1단계 - 멀티 스레드 환경에서 `SqlClient` 객체를 공유할 때 발생하는 문제점을 이해한다.

- com.eomcs.pms.service.impl.DefaultProjectService 변경
  - `delete()` 메서드를 변경한다.
- 테스트
  - A 클라이언트에서 프로젝트 변경을 시도한다.
    - 프로젝트 변경, 기존 멤버의 삭제, 변경한 멤버를 새로 등록한다.
    - 다른 클라이언트가 프로젝트를 삭제할 수 있도록 30초 동안 대기 상태로 만든다.
    - 30초 후에 깨어나서 현재까지 수행했던 변경 작업들을 승인한다.
  - B 클라이언트에서 프로젝트 삭제를 시도한다.
    - A 클라이언트의 작업이 멈춰있는 동안 프로젝트 삭제 작업을 수행한다.
    - 프로젝트의 작업을 삭제하고, 멤버를 삭제한다.
    - 프로젝트를 삭제할 때 예외를 발생시킨다.
    - 기존의 수행했던 프로젝트의 작업 삭제와 멤버 삭제를 취소한다.
  - B 클라이언트 작업이 완료된 후 A 클라이언트 작업이 정상적으로 승인되었는지 확인한다.
    - A 클라이언트의 프로젝트 변경 작업이 승인되지 않았음을 확인한다. 
    - 왜? 
      - B 클라이언트의 요청을 처리하는 중에 예외가 발생하여 임시 데이터베이스에 보관된 모든 변경 결과가 취소됐기 때문이다.
  
### 2단계 - DAO 객체를 생성할 때 `SqlSession` 대신 `SqlSessionFactory` 객체를 주입한다.

- com.eomcs.mybatis.MybatisDaoFactory 변경
  - 생성자에서 `SqlSessionFactory`를 받는다.
  - `DaoWorker` 객체를 생성할 때 `SqlSessionFactory`를 주입한다.
- com.eomcs.mybatis.DaoWorker 변경
  - 생성자에서 `SqlSessionFactory`를 받는다.
  - DAO의 작업을 수행할 때마다 `SqlSessionFactory` 로 `SqlSession` 객체를 만들어 사용한다.
- com.eomcs.pms.service.impl.DefaultXxxService 변경
  - 서비스 객체 생성자에서 `SqlSession` 객체 대신 `SqlSessionFactory`를 주입받는다.
  - `SqlSession` 을 사용할 때마다 `SqlSessionFactory`에서 만들어 쓴다.
- com.eomcs.pms.ServerApp 변경
  - `MybatisDaoFactory`를 생성할 때 `SqlSessionFactory`를 주입한다.
  - 백업: ServerApp01.java 

### 3단계 - 같은 스레드에서 같은 SqlSession 객체를 사용하도록 `SqlSessionFactory`를 커스터마이징 한다.

- 2단계까지는 DAO(`DaoWorker`)가 작업을 할 때마다 매번 새 `SqlSession` 객체를 사용하기 때문에 
  여러 개의 작업을 하나로 묶어 처리할 수 없었다.
- 즉 트랜잭션 제어가 불가능하였다.
- 이번 단계에서는 서비스에서 트랜잭션 제어를 할 수 있도록 `SqlSessionFactory`의 기능을 바꿀 것이다.

- com.eomcs.mybatis.SqlSessionFactoryProxy 추가
  - Mybatis가 제공하는 `SqlSessionFactory`의 소스 코드를 직접 변경할 수는 없다.
  - 대신에 **프록시 패턴** 기법을 이용하여 기존 클래스의 기능을 변경할 것이다.
    - 기존 클래스의 코드를 바꾸지 않고 기능을 변경할 때 많이 사용하는 설계 기법이 **프록시 패턴**이다.
    - 프록시 패턴의 핵심은 실제 객체를 대행할 클래스 또한 실제 객체와 같은 인터페이스를 구현해야 한다는 것이다.
    - 바꾸고 싶은 기능이 있다면 해당 메서드를 재정의하라.
    - 기타 메서드는 원래 객체에게 그대로 위임하면 된다.
- com.eomcs.mybatis.SqlSessionProxy 추가
  - 트랜잭션에 포함된 작업을 실행할 때는 트랜잭션을 종료할 때까지 SqlSession을 닫아서는 안된다.
  - 즉 SqlSession 객체에 대해 close()를 호출하더라도 무시해야 한다.
  - 트랜잭션이 완료되어 더이상 SqlSession을 사용할 필요가 없을 때 진짜로 close 해야 한다.
  - 이렇게 SqlSession의 기존 기능을 변경하기 위해 **프록시 패턴** 을 사용하여 오리지널 객체를 커스터마이징 할 것이다.

### 4단계 - 멀티스레드 환경에서 트랜잭션을 다룰 때 사용할 객체를 정의한다.

- com.eomcs.mybatis.TransactionManager 추가
  - 트랜잭션을 시작하고 종료하는 일을 한다.
  - 스레드에 보관된 SqlSessionProxy 객체를 이용하여 commit/rollback 을 수행한다.
  - 또한 SqlSessionProxy 를 이용해 SqlSession 객체의 자원을 완전히 해제한다.
- com.eomcs.pms.ServerApp 변경
  - 트랜잭션을 관리할 객체를 준비한다.
  - 서비스 객체에 주입한다.
- com.eomcs.pms.service.impl.DefaultXxxService 변경
  - `SqlSessionFactory`를 주입 받는 대신에 `TransactionManager`를 주입받는다.
  - 트랜잭션을 제어할 때 `TransactionManager` 를 사용한다.
  - 백업: DefaultProjectService01.java

### 5단계 - `TransactionManager` 를 이용한 트랜잭션 제어 코드를 캡슐화 한다.

- com.eomcs.mybatis.TransactionCallback 인터페이스 추가
  - 트랜잭션으로 묶어 수행할 작업을 정의하는 객체 
  - `TransactionTemplate`은 이 인터페이스 규칙에 따라 작업을 실행할 것이다.
- com.eomcs.mybatis.TransactionTemplate 클래스 추가
  - 트랜잭션 상태에서 작업을 실행하는 일을 한다.
  - 작업을 정상적으로 완료한다면 commit()을 자동으로 호출한다.
  - 작업에 실패한다면 rollback()을 호출한다.

## 실습 결과
- src/main/java/com/eomcs/mybatis/SqlSessionFactoryProxy.java 추가
- src/main/java/com/eomcs/mybatis/SqlSessionProxy.java 추가
- src/main/java/com/eomcs/mybatis/TransactionManager.java 추가
- src/main/java/com/eomcs/mybatis/MybatisDaoFactory.java 변경
- src/main/java/com/eomcs/mybatis/DaoWorker.java 변경
- src/main/java/com/eomcs/pms/service/impl/DefaultXxxService.java 변경
- src/main/java/com/eomcs/ServerApp.java 변경
