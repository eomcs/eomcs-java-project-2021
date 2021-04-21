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



## 실습 결과
- src/main/java/com/eomcs/ServerApp.java 변경
- src/main/java/com/eomcs/pms/handler/LoginHandler.java 추가
- src/main/java/com/eomcs/pms/handler/UserInfoHandler.java 추가
- src/main/java/com/eomcs/pms/handler/LogoutHandler.java 변경
- src/main/java/com/eomcs/pms/handler/BoardAddHandler.java 변경
- src/main/java/com/eomcs/pms/handler/BoardUpdateHandler.java 변경
- src/main/java/com/eomcs/pms/handler/BoardDeleteHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectAddHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectUpdateHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectDeleteHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectMemberUpdateHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectMemberDeleteHandler.java 변경
- src/main/java/com/eomcs/pms/service/MemberService.java 변경
- src/main/java/com/eomcs/pms/service/impl/DefaultMemberService.java 변경
- src/main/java/com/eomcs/pms/dao/MemberDao.java 변경
- src/main/java/com/eomcs/pms/mapper/MemberMapper.xml 변경
