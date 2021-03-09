
# 29-e. 파일 관리를 별도의 애플리케이션으로 분리하기 : 게시글 관리 기능 구현

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

### 2단계 - 게시글 데이터를 다룰 BoardTable 클래스를 정의한다.

- `com.eomcs.pms.domain.Board` 클래스 추가
    - `mini-pms` 프로젝트에서 해당 파일을 가져온다.
- `com.eomcs.pms.table.BoardTable` 클래스 정의


## 실습 결과
- src/main/java/com/eomcs/pms/ServerApp.java 변경
