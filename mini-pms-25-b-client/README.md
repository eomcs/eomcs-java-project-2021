### 25-b. 비즈니스 로직 분리하기 : 서비스 객체의 도입

이번 훈련에서는,
- *Mybatis* 에서 *트랜잭션* 을 다루는 방법을 배울 것이다.
- 기존 클래스의 코드를 손대지 않고 일부 기능을 변경하는 **프록시 패턴** 설계 기법을 배운다.

## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - BoardDao 구현체에서 업무 로직과 트랜잭션 제어 부분을 분리한다.

- com.eomcs.pms.service.BoardService 클래스 작성
  - 트랜잭션을 제어하기 위해 생성자에서 SqlSession 객체를 주입 받는다.
  - `BoardDaoImpl` 클래스에서 비즈니스 로직과 트랜잭션 처리 코드를 가져온다.
- com.eomcs.pms.dao.mariadb.BoardDaoImpl 클래스 변경
  - 트랜잭션 제어 코드를 제거한다.
- com.eomcs.pms.handler.BoardXxxHandler 클래스 변경
  - *DAO* 객체를 주입하는 대신에 *Service* 객체를 주입하라.
- com.eomcs.pms.ClientApp 클래스 변경
  - *BoardService* 객체를 생성한다.
  - *BoardXxxHandler* 객체에 주입한다.

### 2단계 - MemberDao 구현체에서 업무 로직과 트랜잭션 제어 부분을 분리한다.

- com.eomcs.pms.service.MemberService 클래스 작성
  - 트랜잭션을 제어하기 위해 생성자에서 SqlSession 객체를 주입 받는다.
  - `MemberDaoImpl` 클래스에서 비즈니스 로직과 트랜잭션 처리 코드를 가져온다.
- com.eomcs.pms.dao.mariadb.MemberDaoImpl 클래스 변경
  - 트랜잭션 제어 코드를 제거한다.
- com.eomcs.pms.handler.MemberXxxHandler 클래스 변경
  - *DAO* 객체를 주입하는 대신에 *Service* 객체를 주입하라.
- com.eomcs.pms.ClientApp 클래스 변경
  - *MemberService* 객체를 생성한다.
  - *MemberXxxHandler* 객체에 주입한다.


### 3단계 - ProjectDao 구현체에서 업무 로직과 트랜잭션 제어 부분을 분리한다.

- com.eomcs.pms.service.ProjectService 클래스 작성
  - 트랜잭션을 제어하기 위해 생성자에서 SqlSession 객체를 주입 받는다.
  - `ProjectDaoImpl` 클래스에서 비즈니스 로직과 트랜잭션 처리 코드를 가져온다.
- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 클래스 변경
  - 트랜잭션 제어 코드를 제거한다.
- com.eomcs.pms.dao.mariadb.TaskDaoImpl 클래스 변경
  - `deleteByProjectNo()` 메서드에서 트랜잭션 코드를 제거한다.
- com.eomcs.pms.handler.ProjectXxxHandler 클래스 변경
  - *DAO* 객체를 주입하는 대신에 *Service* 객체를 주입하라.
- com.eomcs.pms.ClientApp 클래스 변경
  - *ProjectService* 객체를 생성한다.
  - *ProjectXxxHandler* 객체에 주입한다.

### 4단계 - TaskDao 구현체에서 업무 로직과 트랜잭션 제어 부분을 분리한다.

- com.eomcs.pms.service.TaskService 클래스 작성
  - 트랜잭션을 제어하기 위해 생성자에서 SqlSession 객체를 주입 받는다.
  - `TaskDaoImpl` 클래스에서 비즈니스 로직과 트랜잭션 처리 코드를 가져온다.
- com.eomcs.pms.dao.mariadb.TaskDaoImpl 클래스 변경
  - 트랜잭션 제어 코드를 제거한다.
- com.eomcs.pms.handler.TaskXxxHandler 클래스 변경
  - *DAO* 객체를 주입하는 대신에 *Service* 객체를 주입하라.
- com.eomcs.pms.ClientApp 클래스 변경
  - *TaskService* 객체를 생성한다.
  - *TaskXxxHandler* 객체에 주입한다.


## 실습 결과

- src/main/java/com/eomcs/pms/service/BoardService.java 생성
- src/main/java/com/eomcs/pms/service/MemberService.java 생성
- src/main/java/com/eomcs/pms/service/ProjectService.java 생성
- src/main/java/com/eomcs/pms/service/TaskService.java 생성
- src/main/java/com/eomcs/pms/dao/mariadb/BoardDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/MemberDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/ProjectDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/TaskDaoImpl.java 변경
- src/main/java/com/eomcs/pms/handler/XxxHandler.java 변경
- src/main/java/com/eomcs/pms/ClientApp.java 변경
