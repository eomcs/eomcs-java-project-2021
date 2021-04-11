### 25-a. 비즈니스 로직 분리하기 : DAO에서 트랜잭션을 다룰 때의 한계

이번 훈련에서는,
- *DAO* 에서 *트랜잭션* 을 제어할 때의 문제점을 확인하고 이해한다.

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

### 1단계 - 프로젝트의 모든 멤버를 삭제하는 기능을 추가한다.

다음과 같이 명령어를 입력했을 때 동작하게 만들라.
```
명령> /project/memberDelete
[프로젝트 멤버 삭제]
프로젝트 번호? 10
프로젝트 명: pp4
멤버:
  dd(4)
  ee(5)
  ff(6)

정말 삭제하시겠습니까?(y/N) y
프로젝트 멤버를 삭제하였습니다.

명령>
```

- com.eomcs.pms.handler.ProjectMemberDeleteHandler 클래스 추가
  - 프로젝트의 모든 멤버를 삭제한다.
- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 클래스 변경
  - `deleteMembers()`를 변경한다.
    - 데이터를 삭제한 후에 commit 을 수행한다.
    - 그래야만 ProjectMemberDeleteHandler 에서 정상적으로 프로젝트 멤버의 삭제를 진행할 수 있다.
- 문제점:
  - `deleteMembers()`는 프로젝트를 삭제할 때도 사용한다.
  - 즉 `delete()` 메서드 안에서 호출한다.
  - 문제는 `delete()` 메서드에서 rollback을 실행할 때
    `deleteMembers()` 가 작업한 결과는 취소되지 않는다는 것이다.
  - 왜? 이미 deleteMembers() 에서 커밋했기 때문이다.


### 2단계 - 프로젝트의 멤버를 변경하는 기능을 추가한다.

다음과 같이 명령어를 입력했을 때 프로젝트 멤버를 변경하도록 기능을 추가하라.
```
명령> /project/memberUpdate
[프로젝트 멤버 변경]
프로젝트 번호? 15
프로젝트 명: pp6
멤버:

프로젝트의 멤버를 새로 등록하세요.
팀원?(완료: 빈 문자열) dd
팀원?(완료: 빈 문자열) cc
팀원?(완료: 빈 문자열) ee
팀원?(완료: 빈 문자열)
정말 변경하시겠습니까?(y/N) y
프로젝트 멤버를 변경하였습니다.

명령>
```

- com.eomcs.pms.handler.ProjectMemberUpdateHandler 클래스 추가
  - 기존의 `ProjectUpdateHandler` 클래스를 가져와서 편집하라.
  - 기존 프로젝트의 멤버를 모두 삭제한 후 새 프로젝트 멤버를 등록한다.
  - 즉 `deleteMembers()`를 실행한 후 `insertMembers()`를 실행한다.
- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 클래스 변경
  - 기존에는 `insertMember()`, `insertMembers()` 메서드에 대해
    insert()/update()/delete() 을 호출할 때 사용했기 때문에
    따로 커밋을 수행하지 않았다.
  - 그러나 이제 이들 메서드는 위의 핸들러 클래스에서 독자적으로 호출하기 때문에
    따로 커밋을 수행해야 한다.
  - `insertMember()`, `insertMembers()` 메서드를 변경하라.
    - 입력 후 commit 을 수행하라.
- 문제점:
  - `deleteMembers()`를 실행한 후 `insertMembers()`를 실행할 때 예외가 발생한다면?
    - insertMembers()에서 rollback을 할 것이다.
  - 그러나 deleteMembers()가 작업한 결과를 취소되지 않는다.
    - 왜? deleteMembers() 에서 이미 commit 했기 때문이다.


### 3단계 - DAO의 각 메서드가 트랜잭션을 다룰 경우 발생하는 문제점을 이해한다.

- com.eomcs.pms.handler.ProjectDeleteHandler 실행
  - TaskDao를 통해 해당 프로젝트의 작업들을 모두 삭제한다.
  - 그런 후에 ProjectDao를 통해 프로젝트를 삭제한다.
- 문제점?
  - 프로젝트 삭제 중 예외가 발생했을 때,
    TaskDao를 통해 수행한 작업 삭제는 취소할 수 없다.
  - 왜? `TaskDao.deleteByProjectNo()`에서 이미 commit 했기 때문이다.

- **DAO** 객체에서 트랜잭션을 다루면 안되는 이유?
  - **DAO** 의 각 메서드에서 commit/rollback을 하게 되면
    다른 메서드와 묶어서 한 트랜잭션으로 제어할 수 없다.
  - 즉 실습 상황처럼 DAO 각 메서드 마다 트랜잭션을 따로 제어하면
    여러 DAO의 메서드를 묶어서 한 단위로 작업할 때
    통제할 수 없는 문제가 발생한다.
  - 해결책?
    - **DAO** 의 각 메서드가 트랜잭션을 통제하지 않도록 만든다.
    - 그럼 누가 트랜잭션을 통제하는가?
      - **DAO** 를 사용하는 객체가 통제하게 한다.

## 실습 결과

- src/main/java/com/eomcs/pms/dao/mariadb/ProjectDaoImpl.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectMemberDeleteHandler.java 추가
- src/main/java/com/eomcs/pms/handler/ProjectMemberUpdateHandler.java 추가
- src/main/java/com/eomcs/pms/ClientApp.java 변경
