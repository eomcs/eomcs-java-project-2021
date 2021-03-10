# 29-e. 파일 관리를 별도의 애플리케이션으로 분리하기 : 게시글 데이터 처리 구현

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

### 1단계 - 요청/응답 프로토콜에 맞춰 서버에 요청하고 응답을 처리한다.

- `com.eomcs.pms.ClientApp` 변경

## 실습 결과
- src/main/java/com/eomcs/pms/ClientApp.java 변경
