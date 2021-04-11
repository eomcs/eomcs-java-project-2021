
# 21-e. 데이터 관리 서버 만들기 : 파일 및 데이터 처리 기능을 서버로 이전

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
- 응답하는 코드를 별도의 메서드로 분리한다.

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
board/insert (UTF-8 문자열)
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

예) board/insert 요청에 대한 응답
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

### 1단계 - 클라이언트가 요청한 데이터를 처리할 객체의 규칙을 정의한다.

- `com.eomcs.pms.table.DataTable` 인터페이스 정의

### 2단계 - JSON 데이터를 다룰 JsonFileHandler 클래스를 정의한다.

- `google-gson` 자바 라이브러리를 프로젝트에 추가한다.
    - build.gradle 에 의존 라이브러리 정보를 추가한 후 이클립스 설정 파일을 갱신한다.
- `com.eomcs.util.JsonFileHandler` 클래스 정의
    - `loadObjects()`, `saveObjects()` 스태틱 메서드를 정의한다.

### 3단계 - 클라이언트의 요청 처리와 관련된 클래스를 정의한다.

- `com.eomcs.util.Request` 클래스 정의
    - 클라이언트의 요청 정보(명령과 데이터 목록)를 다루는 클래스다.
- `com.eomcs.util.Response` 클래스 정의
    - 서버의 응답 데이터를 다룰 클래스다.

### 4단계 - 게시글 데이터를 다룰 BoardTable 클래스를 정의한다.

- `com.eomcs.pms.domain.Board` 클래스 추가
    - `mini-pms` 프로젝트에서 해당 파일을 가져온다.
- `com.eomcs.pms.table.BoardTable` 클래스 정의
    - 클라이언트가 요청한 명령에 데이터를 처리하고 응답 데이터를 준비한다.
    - insert/update/delete 명령을 처리한 후에 즉시 파일을 저장한다.

### 5단계 - 요청/응답 프로토콜에 따라 클라이언트 요청을 처리한다.

- `com.eomcs.pms.ServerApp` 클래스 변경
    - 클라이언트 요청 처리 코드에 대해 리팩토링을 수행한다.
    - `BoardTable` 객체를 테이블 맵에 추가한다.

### 6단계 - 회원 데이터를 다룰 MemberTable 클래스를 정의한다.

- `com.eomcs.pms.domain.Member` 클래스 추가
    - `mini-pms` 프로젝트에서 해당 파일을 가져온다.
- `com.eomcs.pms.table.MemberTable` 클래스 정의
    - 클라이언트가 요청한 명령에 데이터를 처리하고 응답 데이터를 준비한다.
    - insert/update/delete 명령을 처리한 후에 즉시 파일을 저장한다.
- `com.eomcs.pms.ServerApp` 클래스 변경
    - `MemberTable` 객체를 테이블 맵에 추가한다.

### 7단계 - 프로젝트 데이터를 다룰 ProjectTable 클래스를 정의한다.

- `com.eomcs.pms.domain.Project` 클래스 추가
    - `mini-pms` 프로젝트에서 해당 파일을 가져온다.
- `com.eomcs.pms.table.ProjectTable` 클래스 정의
    - 클라이언트가 요청한 명령에 데이터를 처리하고 응답 데이터를 준비한다.
    - insert/update/delete 명령을 처리한 후에 즉시 파일을 저장한다.
- `com.eomcs.pms.table.MemberTable` 클래스 변경
    - 회원 이름으로 데이터를 조회하는 기능 추가
- `com.eomcs.pms.ServerApp` 클래스 변경
    - `ProjectTable` 객체를 테이블 맵에 추가한다.

### 8단계 - 작업 데이터를 다룰 TaskTable 클래스를 정의한다.

- `com.eomcs.pms.domain.Task` 클래스 추가
    - `mini-pms` 프로젝트에서 해당 파일을 가져온다.
- `com.eomcs.pms.table.ProjectTable` 클래스 정의
    - 클라이언트가 요청한 명령에 데이터를 처리하고 응답 데이터를 준비한다.
    - insert/update/delete 명령을 처리한 후에 즉시 파일을 저장한다.
- `com.eomcs.pms.ServerApp` 클래스 변경
    - `TaskTable` 객체를 테이블 맵에 추가한다.

## 실습 결과
- build.gradle 변경
- src/main/java/com/eomcs/util/JsonFileHandler.java 추가
- src/main/java/com/eomcs/util/Request.java 추가
- src/main/java/com/eomcs/util/Response.java 추가
- src/main/java/com/eomcs/pms/table/DataTable.java 추가
- src/main/java/com/eomcs/pms/domain/Board.java 추가
- src/main/java/com/eomcs/pms/domain/Member.java 추가
- src/main/java/com/eomcs/pms/domain/Project.java 추가
- src/main/java/com/eomcs/pms/domain/Task.java 추가
- src/main/java/com/eomcs/pms/table/BoardTable.java 추가
- src/main/java/com/eomcs/pms/table/MemberTable.java 추가
- src/main/java/com/eomcs/pms/table/ProjectTable.java 추가
- src/main/java/com/eomcs/pms/table/TaskTable.java 추가
- src/main/java/com/eomcs/pms/ServerApp.java 변경
