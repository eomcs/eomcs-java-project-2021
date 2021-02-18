# 24-b. 'Command' 디자인 패턴을 적용하기 : 메서드를 객체로 분리하기

이번 훈련에서는 **커맨드 패턴(command pattern)** 을 프로젝트에 적용할 것이다.

**커맨드 디자인 패턴** 은, 

- *메서드의 객체화* 설계 기법이다.
- 한 개의 명령어를 처리하는 메서드를 별개의 클래스로 분리하는 기법이다. 
- 이렇게 하면 명령어가 추가될 때마다 새 클래스를 만들면 되기 때문에  
  기존 코드를 손대지 않아서 유지보수에 좋다.
- 즉 기존 소스에 영향을 끼치지 않고 새 기능을 추가하는 방식이다.
- 명령처리를 별도의 객체로 분리하기 때문에 실행 내역을 관리하기 좋고,
  각 명령이 수행했던 작업을 다루기가 편하다.
- 인터페이스를 이용하면 메서드 호출 규칙을 단일화 할 수 있기 때문에 
  코딩의 일관성을 높혀줄 수 있다.
- 단 기능 추가할 때마다 해당 기능을 처리하는 새 클래스가 추가되기 때문에 
  클래스 개수는 늘어난다.
- 그러나 유지보수 측면에서는 기존 코드를 변경하는 것 보다는 
  클래스 개수가 늘어나는 것이 좋다.
- 유지보수 관점에서는 소스 코드를 일관성 있게 유지보수 할 수 있는게 더 중요한다.


## 훈련 목표

- **커맨드 패턴** 의 클래스 구조와 구동원리를 이해한다.
- **커맨드 패턴** 을 구현하는 방법을 배운다.


## 훈련 내용

- 사용자 명령을 처리할 때 호출할 메서드의 규칙을 인터페이스로 정의한다.
- 명령어를 처리하는 메서드를 인터페이스에 맞춰 별개의 클래스로 캡슐화 한다. 

## 실습

`mini-pms-23` 소스를 기반으로 작업한다. 

### 1단계 - 게시판 명령을 처리하는 BoardHandler 의 각 메서드를 별도의 클래스로 분리한다.

- 각 명령어를 처리하는 메서드를 별도의 BoardXxxHandler 클래스를 만들어 분리한다.
  - BoardAddHandler, BoardListHandler, BoardDetailHandler,BoardUpdateHandler, BoardDeleteHandler
- 게시판 데이터를 공유하기 위해 생성자를 통해 `List` 객체를 주입 받는다.

### 작업 파일

- com.eomcs.pms.handler.BoardAddHandler 생성
- com.eomcs.pms.handler.BoardListHandler 생성
- com.eomcs.pms.handler.BoardDetailHandler 생성
- com.eomcs.pms.handler.BoardUpdateHandler 생성
- com.eomcs.pms.handler.BoardDeleteHandler 생성
- com.eomcs.pms.handler.BoardHandler 삭제
- com.eomcs.pms.App 변경

### 2단계 - 여러 클래스에 공통으로 등장하는 필드와 메서드를 별도의 클래스로 분리한다.

- `BoardHandler` 클래스를 정의한다.
  - BoardXxxHandler 클래스에 있던 공통 멤버인 `boardList`와 `findByNo()` 메서드를 이 클래스로 가져온다.
  - BoardXxxHandler에서 이 클래스를 상속 받도록 한다.

### 작업 파일

- com.eomcs.pms.handler.BoardAddHandler 변경
- com.eomcs.pms.handler.BoardListHandler 변경
- com.eomcs.pms.handler.BoardDetailHandler 변경
- com.eomcs.pms.handler.BoardUpdateHandler 변경
- com.eomcs.pms.handler.BoardDeleteHandler 변경
- com.eomcs.pms.handler.BoardHandler 다시 생성


### 3단계 - generalization을 통해 만든 수퍼 클래스를 추상 클래스로 변경한다.

- `BoardHandler` 클래스를 추상 클래스로 변경한다.
  - 클래스를 추상클래스로 만들어 상속 받는 용도로만 사용하게 한다.
  - 클래스의 이름을 `AbstractBoardHandler` 로 변경한다.

### 작업 파일

- com.eomcs.pms.handler.AbstractBoardHandler 이름 변경


### 4단계 - 나머지 `MemberHandler`, `ProjectHandler`, `TaskHandler` 도 Command 패턴을 적용한다.


#### 작업 파일
 
- com.eomcs.pms.handler.MemberAddCommand 생성
- com.eomcs.pms.handler.MemberListCommand 생성
- com.eomcs.pms.handler.MemberDetailCommand 생성
- com.eomcs.pms.handler.MemberUpdateCommand 생성
- com.eomcs.pms.handler.MemberDeleteCommand 생성
- com.eomcs.pms.handler.AbstractMemberHandler 생성
- com.eomcs.pms.handler.MemberHandler 삭제
- com.eomcs.pms.handler.ProjectAddCommand 생성
- com.eomcs.pms.handler.ProjectListCommand 생성
- com.eomcs.pms.handler.ProjectDetailCommand 생성
- com.eomcs.pms.handler.ProjectUpdateCommand 생성
- com.eomcs.pms.handler.ProjectDeleteCommand 생성
- com.eomcs.pms.handler.AbstractProjectHandler 생성
- com.eomcs.pms.handler.ProjectHandler 삭제
- com.eomcs.pms.handler.TaskAddCommand 생성
- com.eomcs.pms.handler.TaskListCommand 생성
- com.eomcs.pms.handler.TaskDetailCommand 생성
- com.eomcs.pms.handler.TaskUpdateCommand 생성
- com.eomcs.pms.handler.TaskDeleteCommand 생성
- com.eomcs.pms.handler.AbstractTaskHandler 생성
- com.eomcs.pms.handler.TaskHandler 삭제
- com.eomcs.pms.App 변경

### 5단계 - [리팩토링] `AbstractMemberHandler`에서 회원 유효성 검사를 하는 메서드를 별도의 클래스로 분리한다.

- `MemberValidatorHandler` 클래스 추가 
  - `AbstractMemberHandler`에 있는 `inputMember()`와 `inputMembers()`를 이 클래스로 옮긴다.
- `ProjectAddHandler`, `ProjectUpdateHandler`, `TaskAddHandler`, `TaskUpdateHandler` 클래스의 의존 객체를 `MemberValidatorHandler`로 교체한다.


#### 작업 파일
 
- com.eomcs.pms.handler.MemberValidatorHandler 생성
- com.eomcs.pms.handler.ProjectAddCommand 변경
- com.eomcs.pms.handler.ProjectUpdateCommand 변경
- com.eomcs.pms.handler.TaskAddCommand 변경
- com.eomcs.pms.handler.TaskUpdateCommand 변경
- com.eomcs.pms.App 변경





### 3단계 - 사용자가 명령어를 입력했을 때 `Command` 구현체를 실행하도록 변경한다.

- `App` 클래스가 XxxCommand 객체를 통해 처리하도록 변경한다.

#### 작업 파일

- com.eocms.pms.App 클래스 변경


### 4단계 - `/hello` 명령을 추가한다.

- 커맨드 패턴을 적용하여 애플리케이션 아키텍처를 변경되었다.
- 커맨드 패턴을 적용할 경우 새 기능 추가가 쉽다는 것을 확인해보자. 

```console
명령> /hello
안녕하세요!

명령>
```

#### 작업 파일

- com.eomcs.pms.handler.HelloCommand 생성


## 실습 결과

- src/main/java/com/eomcs/pms/handler/Command.java 생성
- src/main/java/com/eomcs/pms/handler/BoardAddCommand.java 생성
- src/main/java/com/eomcs/pms/handler/BoardListCommand.java 생성
- src/main/java/com/eomcs/pms/handler/BoardDetailCommand.java 생성
- src/main/java/com/eomcs/pms/handler/BoardUpdateCommand.java 생성
- src/main/java/com/eomcs/pms/handler/BoardDeleteCommand.java 생성
- src/main/java/com/eomcs/pms/handler/BoardHandler.java 삭제
- src/main/java/com/eomcs/pms/handler/MemberAddCommand.java 생성
- src/main/java/com/eomcs/pms/handler/MemberListCommand.java 생성
- src/main/java/com/eomcs/pms/handler/MemberDetailCommand.java 생성
- src/main/java/com/eomcs/pms/handler/MemberUpdateCommand.java 생성
- src/main/java/com/eomcs/pms/handler/MemberDeleteCommand.java 생성
- src/main/java/com/eomcs/pms/handler/MemberHandler.java 삭제
- src/main/java/com/eomcs/pms/handler/ProjectAddCommand.java 생성
- src/main/java/com/eomcs/pms/handler/ProjectListCommand.java 생성
- src/main/java/com/eomcs/pms/handler/ProjectDetailCommand.java 생성
- src/main/java/com/eomcs/pms/handler/ProjectUpdateCommand.java 생성
- src/main/java/com/eomcs/pms/handler/ProjectDeleteCommand.java 생성
- src/main/java/com/eomcs/pms/handler/ProjectHandler.java 삭제
- src/main/java/com/eomcs/pms/handler/TaskAddCommand.java 생성
- src/main/java/com/eomcs/pms/handler/TaskListCommand.java 생성
- src/main/java/com/eomcs/pms/handler/TaskDetailCommand.java 생성
- src/main/java/com/eomcs/pms/handler/TaskUpdateCommand.java 생성
- src/main/java/com/eomcs/pms/handler/TaskDeleteCommand.java 생성
- src/main/java/com/eomcs/pms/handler/TaskHandler.java 삭제
- src/main/java/com/eomcs/pms/handler/HelloCommand.java 생성
- src/main/java/com/eomcs/pms/App.java 변경

  