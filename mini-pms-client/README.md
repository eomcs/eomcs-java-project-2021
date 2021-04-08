### 32-c. DB 프로그래밍을 더 쉽고 간단히 하는 방법 : Mybatis에서 트랜잭션 다루기

이번 훈련에서는,
- *Mybatis* 에서 *트랜잭션* 을 다루는 방법을 배울 것이다.
- 기존 클래스의 코드를 손대지 않고 일부 기능을 변경하는 **프록시 패턴** 설계 기법을 배운다.

**프록시(proxy) 디자인 패턴** 은,
- 특정 객체의 접근을


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

### 1단계 - 자동 커밋의 문제점을 이해한다.

문제 상황 실습:
- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 변경
  - `ProjectDaoImpl` 클래스의 `insert()` 메서드를 호출할 때,
    *프로젝트 정보를 등록* 한 후 바로 다음에 예외를 발생시키자.
  - 그러면 프로젝트 멤버가 등록되지 않은채로 프로젝트 기본 정보가 등록된다.
  - 즉 프로젝트 등록이 온전히 완료되지 않는다.
- 이유?
  - SqlSession 객체가 자동 커밋 모드이기 때문이다.
  - 데이터 변경(insert/update/delete) 작업을 수행하면 그 즉시 테이블에 변경 결과를 적용한다.
- 실습
  - `/project/add` 명령을 실행하여 프로젝트 정보 등록을 수행해보라!
  - 그리고 프로젝트 정보만 등록된 것을 확인해 보라!

### 2단계 - 트랜잭션을 다루기 위해 수동 커밋 모드 활용하기 

- 자동 커밋 문제의 해결
  - 프로젝트 등록과 프로젝트 멤버 등록은 함께 묶어서 처리해야하는 작업이다.
  - 이 두 작업은 한 트랜잭션으로 다뤄야 한다.
- Mybatis에서 트랜잭션을 다루는 방법
  - SqlSessionFactory에서 SqlSession 객체를 생성할 때 수동 커밋 모드로 생성한다.
  - 그런데 현재 코드는 SqlSession을 생성할 때 자동 커밋 모드로 생성하였다.

- com.eomcs.pms.ClientApp 클래스 변경
  - SqlSession 객체를 얻을 때 수동 커밋 모드로 생성한다.
- com.eomcs.pms.dao.mariadb.XxxDaoImpl 클래스 변경
  - insert/update/delete 을 실행한 후에는 반드시 commit()을 호출한다.
  - 여러 개의 작업을 묶은 경우에는 예외가 발생했을 때 rollback()을 반드시 호출한다.

### 3단계 - DAO의 각 메서드에서 트랜잭션을 제어할 때 발생하는 문제점 확인





- **DAO** 객체에서 트랜잭션을 다루면 안되는 이유?
  - **DAO** 의 각 메서드는 작업을 수행하기 위해 현재 별도의 `SqlSession` 객체를 사용한다.
  - 트랜잭션은 `SqlSession` 객체에서 제어한다.
  - 즉 DAO 각 메서드 마다 트랜잭션이 분리되어 있다.
  - 실습 상황처럼 DAO 각 메서드 마다 트랜잭션이 분리되어 있으면,
    여러 DAO의 메서드를 묶어서 한 단위로 작업할 때
    통제할 수 없는 문제가 발생한다.
  - 해결책?
    - **DAO** 의 각 메서드가 트랜잭션을 통제하지 않도록 만든다.
    - 그럼 누가 트랜잭션을 통제하는가?
      - **DAO** 를 사용하는 **Command** 객체가 통제하게 한다.
      - 즉 트랜잭션 통제권을 **DAO** 를 사용하는 객체로 넘긴다.

### 3단계 - `Command` 객체에서 트랜잭션을 통제해 보자!

- com.eomcs.util.SqlSessionProxy 클래스 생성
  - Mybatis 의 `SqlSession` 구현체의 대리 역할을 수행할 클래스를 정의한다.
  - `close()` 메서드를 재정의한다.
  - 트랜잭션을 수행 중인 상태에서는 `close()`가 동작되지 않도록 막는다.
- com.eomcs.util.SqlSessionFactoryProxy 클래스 생성
  - Mybatis 의 `SqlSessionFactory` 구현체의 대리 역할을 수행할 클래스를 정의한다.
  - `startTransaction()`, `endTransaction()` 메서드 추가
  - `commit()`, `rollback()` 메서드 추가
  - `openSession()` 메서드 재정의
- com.eomcs.pms.listener.AppInitListener 클래스 변경
  - DAO 객체에 오리지널 `SqlSessionFactory` 대신에 프록시 객체를 주입한다.
- com.eomcs.pms.dao.mariadb.TaskDaoImpl 클래스 변경
  - `deleteByProjectNo()` 메서드에서 SqlSession 을 얻을 때 수동 커밋 상태의
    `SqlSession` 을 사용하도록 변경한다.
  - 왜? 다른 작업과 묶을 수 있도록 하기 위함이다.
- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 클래스 변경
  - `delete()` 메서드에서 트랜잭션을 제어하는 코드를 없앤다.
    - `commit()` 호출하는 코드를 없앤다.
  - 왜? 트랜잭션 제어는 DAO를 사용하는 측에서 해야하기 때문이다.
  - 상황에 따라 여러 개의 DAO에서 수행한 작업을 한 트랜잭션을 묶어서 다룰 경우가 있다.
  - 이런 상황에서 각각의 DAO가 commit()/rollback() 을 하게 되면
    트랜잭션 제어가 안되기 때문이다.
- com.eomcs.pms.handler.ProjectDeleteCommand 클래스 변경
  - 여러 작업을 트랜잭션으로 묶어서 다룰 경우 트랜잭션 제어는 Command 객체에서 한다.
  - 예외없이 실행이 정상적으로 완료되었다면, SqlSessionFactoryProxy 에게 commit 요청한다.
  - 생성자에서 SqlSessionFactoryProxy 를 받아야 한다.

### 4단계 - 모든 DAO 클래스에서 자동 커밋을 수동 커밋으로 변경한다.

- com.eomcs.pms.dao.mariadb.XxxDaoImpl 클래스 변경
  - `openSession(true)` 코드를 `openSession()` 으로 변경한다.
  - 즉 트랜잭션 통제권을 DAO를 사용하는 측에 넘긴다.

### 5단계 - 프로젝트 정보를 변경할 때 팀원 정보는 변경하지 않는다.
- com.eomcs.pms.handler.ProjectUpdateCommand 클래스 변경
  - 프로젝트의 정보를 변경할 때 팀원 정보를 변경하지 않는다.
- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 클래스 변경
  - 프로젝트 정보를 변경할 때 회원 정보를 변경하지 않는다.

## 실습 결과

- src/main/resources/com/eomcs/pms/mapper/TaskMapper.xml 생성
- src/main/java/com/eomcs/pms/dao/TaskDao.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/TaskDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/ProjectDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/BoardDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/MemberDaoImpl.java 변경
- src/main/java/com/eomcs/pms/handler/TaskListCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectDetailCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectDeleteCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectUpdateCommand.java 변경
- src/main/java/com/eomcs/util/SqlSessionProxy.java 생성
- src/main/java/com/eomcs/util/SqlSessionFactoryProxy.java 생성
- src/main/java/com/eomcs/pms/listener/AppInitListener.java 변경
