# 21-f. 데이터 관리 서버 만들기 : 드라이버 객체 도입

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


## 실습


### 1단계 - 서버측에서 제공하는 Statement 클래스를 가져온다.

- `com.eomcs.driver.Statement` 클래스 복사


### 2단계 - 핸들러 클래스에서 Statement를 사용하여 서버에 요청한다.

- `com.eomcs.pms.handler.Command` 인터페이스 변경
    - service() 메서드의 파라미터를 Statement 객체로 변경한다.
- `com.eomcs.pms.handler.XxxHandler` 클래스 변경
    - 서버와 직접 통신하는 대신에 Statement 객체를 사용해서 서버와 통신한다.
    - 생성자를 통해 의존 객체 Statement 를 주입한다.
- `com.eomcs.pms.ClientApp` 변경
    - `XxxHandler` 객체를 생성할 때 의존 객체를 주입한다.



## 실습 결과

- src/main/java/com/eomcs/pms/handler/Command.java 변경
- src/main/java/com/eomcs/pms/handler/XxxHandler.java 변경
- src/main/java/com/eomcs/pms/handler/MemberValidator.java 변경
- src/main/java/com/eomcs/driver/Statement.java 추가
- src/main/java/com/eomcs/pms/ClientApp.java 변경
