### 32-c. DB 프로그래밍을 더 쉽고 간단히 하는 방법 : Mybatis에서 트랜잭션 다루기

이번 훈련에서는,
- *Mybatis* 에서 *트랜잭션* 을 다루는 방법을 배울 것이다.

## 훈련 목표
- `SqlSession` 객체를 통해 트랜잭션을 다루는 방법을 연습한다.


## 훈련 내용
- 프로젝트의 상세 정보를 출력할 때 작업 목록을 추가한다.
- DAO의 메서드에서 트랜잭션을 다룰 때 문제가 되는 상황을 경험한다.
- `SqlSession` 객체를 통해 트랜잭션을 통제하는 방법을 배운다.

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
  - `/project/list` 명령을 실행하여 입력 오류가 발생한 프로젝트의 정보가 
    등록된 것을 확인하라!
  - 물론 그 프로젝트의 멤버는 등록되지 않았을 것이다.

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

### 3단계 - 트랜잭션의 동작 확인하기

- 1단계의 실습을 다시 수행한다.
  - `/project/add` 명령을 실행하여 프로젝트 정보 등록을 수행해보라!
  - `/project/list` 명령을 실행하여 입력 오류가 발생한 프로젝트의 정보가 
    등록되지 않은 것을 확인하라!

## 실습 결과

- src/main/java/com/eomcs/pms/ClientApp.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/TaskDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/ProjectDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/BoardDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/MemberDaoImpl.java 변경

