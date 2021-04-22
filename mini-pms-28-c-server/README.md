# 28-c. 세션(Session) 다루기 : Stateless 통신에서 세션 유지 방법

이번 훈련에서는,
- **Stateless** 방식으로 통신을 할 때 같은 클라이언트에 대해 같은 세션을 유지하는 방법을 배울 것이다.

## 훈련 목표
- 

## 훈련 내용
- 


## 실습

### 1단계 - 클라이언트와 서버 사이에 세션을 구분하기 위한 값을 주고 받을 수 있도록 프로토콜을 변경한다.

클라이언트 요청 프로토콜
```
명령 (CRLF)
요청 헤더(부가정보) (CRLF)
빈 줄

예)
/board/list (CRLF)
SESSION_ID:세션번호 (CRLF)
CRLF
```

서버 응답 프로토콜
```
응답상태 (CRLF)
응답헤더 (CRLF)
빈줄
응답내용
빈줄

예)
OK (CRLF)
SESSION_ID:세션번호 (CRLF)
CRLF
[회원 목록] (CRLF)
1, aa, aa@test.com, aa.gif, 11111 (CRLF)
2, bb, bb@test.com, bb.gif, 22222 (CRLF)
11, cc, cc@test.com, cc.gif, ttcc (CRLF)
5, dd1, dd1@test.com, dd1.gif, teldd (CRLF)
CRLF
```

- `com.eomcs.pms.ServerApp` 변경
  - 클라이언트에서 세션 아이디를 보내지 않았다면 새로 세션 객체를 만든다.
  - 클라이언트에서 세션 아이디를 보냈다면,
    - 그 아이디에 해당하는 세션이 있다면 그 세션 객체를 사용한다.
    - 그 아이디에 해당하는 세션이 없다면 새로 세션 객체를 만든다.
  - 새로 세션 객체를 만든 경우 응답할 때 세션 아이디를 보낸다.


## 실습 결과
- src/main/java/com/eomcs/pms/ServerApp.java 변경
