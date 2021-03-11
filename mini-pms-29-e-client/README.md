# 29-e. 파일 관리를 별도의 애플리케이션으로 분리하기 :  파일 및 데이터 처리 기능을 서버로 이전
이번 훈련에서는,
- **네트워크 API** 를 이용하여 데스크톱 애플리케이션을 클라이언트/서버 구조로 변경한다.

데스크톱(desktop) 애플리케이션은,
- 다른 애플리케이션과 연동하지 않고 단독적으로 실행한다.
- 보통 PC나 노트북에 설치해서 사용한다.
- 예) MS-Word, Adobe Photoshop, 메모장 등

클라이언트(Client)/서버(Server) 애플리케이션은,
- 줄여서 C/S 애플리케이션이라 부른다.
- 클라이언트는 서버에게 서비스나 자원을 요청하는 일을 한다.
- 서버는 클라이언트에게 자원이나 서비스를 제공하는 일을 한다.

## 훈련 목표
- 통신 프로토콜을 이해한다.
- `extract method` 리팩토링 기법을 연습한다.

## 훈련 내용
- 응답 프로토콜을 변경하고 그에 맞게 구현한다.
- 응답을 수신하는 코드를 별도의 메서드로 분리한다.

### 요청 프로토콜

```
[데이터]/[명령](UTF-8 문자열)
보내는 데이터의 개수(int)
데이터(UTF-8 문자열)

예) 게시글 전체 목록 
board/selectall (UTF-8 문자열)
0 (int)

예) 게시글 상세 목록
board/select (UTF-8 문자열)
1 (int)
100 (UTF-8 문자열)

예) 게시글 등록
board/add (UTF-8 문자열)
1 (int)
제목,내용,작성자 (UTF-8 문자열)

예) 게시글 변경
board/update (UTF-8 문자열)
1 (int)
100,제목,내용 (UTF-8 문자열)

예) 게시글 삭제
board/delete (UTF-8 문자열)
1 (int)
100 (UTF-8 문자열)
```

### 응답 프로토콜

```
success|error(UTF-8 문자열)
보내는 데이터의 개수(int)
데이터 또는 오류 메시지(UTF-8 문자열)

예) board/selectall 요청에 대한 응답
success (UTF-8 문자열)
3 (int)
101,제목,작성자,작성일,조회수 (UTF-8 문자열)
102,제목,작성자,작성일,조회수 (UTF-8 문자열)
103,제목,작성자,작성일,조회수 (UTF-8 문자열)


예) board/select 요청에 대한 응답
success (UTF-8 문자열)
1 (int)
101,제목,내용,작성자,작성일,조회수 (UTF-8 문자열)

예) board/add 요청에 대한 응답
success (UTF-8 문자열)
0 (int)

예) board/update 요청에 대한 응답
success (UTF-8 문자열)
0 (int)

예) board/delete 요청에 대한 응답
success (UTF-8 문자열)
0 (int)
```

## 실습

기존의 **mini-pms-28-b** 프로젝트에서 게시글 관련 코드를 가져와 클라이언트에 적용한다.

### 1단계 - domain 클래스 준비

- `com.eomcs.pms.domain.Board` 클래스 복사 및 편집
- `com.eomcs.pms.domain.Member` 클래스 복사 및 편집
- `com.eomcs.pms.domain.Project` 클래스 복사 및 편집
- `com.eomcs.pms.domain.Task` 클래스 복사 및 편집
    - 상태 번호로 라벨을 리턴하는 `getStatusLabel()` 메서드를 추가한다.

### 2단계 - 핸들러 클래스 준비

- `com.eomcs.pms.handler.Command` 인터페이스 복사 및 변경
    - 서버와 통신을 할 입출력 스트림을 파라미터로 받는다.
- `com.eomcs.pms.handler.XxxHandler` 클래스 복사 및 변경
    - 직접 Command 인터페이스를 구현한다.
    - 서버와 통신하여 데이터를 다루도록 변경한다.
- `com.eomcs.pms.handler.MemberValidator` 클래스 추가
    - 기존의 `MemberValidatorHandler` 클래스를 가져와서 변경한다.

### 3단계 - 데이터를 다룰 때는 서버에 위임한다.

- `com.eomcs.pms.ClientApp` 변경
    - `mini-pms-28-b` 프로젝트의 App 클래스에서 사용자 명령을 입력 받아서 처리하는 부분을 복사해 온다.

    
## 실습 결과

- src/main/java/com/eomcs/pms/domain/Board.java 추가
- src/main/java/com/eomcs/pms/domain/Member.java 추가
- src/main/java/com/eomcs/pms/domain/Project.java 추가
- src/main/java/com/eomcs/pms/domain/Task.java 추가
- src/main/java/com/eomcs/pms/handler/Command.java 추가
- src/main/java/com/eomcs/pms/handler/XxxHandler.java 추가
- src/main/java/com/eomcs/pms/handler/MemberValidator.java 추가
- src/main/java/com/eomcs/pms/ClientApp.java 변경
