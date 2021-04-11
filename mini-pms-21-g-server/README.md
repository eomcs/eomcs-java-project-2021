# 21-g. 데이터 관리 서버 만들기 : 다중 클라이언트의 동시 접속 처리

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
- 스레드를 이용하여 다중 클라이언트 접속을 동시에 처리하는 것을 연습한다.
- 스레드를 만드는 다양한 방법을 배운다.
- 중첩 클래스와 람다 문법의 사용을 연습한다.

## 훈련 내용
- 자바 프로젝트로 사용할 폴더를 만들고 Gradle 빌드 도구로 초기화시킨다.
- Gradle eclipse 플러그인을 사용하여 Eclipse IDE 용 설정 파일을 준비한다.


## 실습

### 1단계 - 클라이언트의 요청 처리 부분을 별도의 스레드로 분리하여 처리한다.

- `com.eomcs.pms.StatementHandlerThread` 클래스 추가
  - `Thread` 를 상속 받는다.
  - 클라이언트 측 Statement 연결 및 요청 처리를 담당한다.
  - `ServerApp` 에서 해당 코드를 가져온다.
- `ServerApp` 클래스 변경
  - 클라이언트 요청 처리를 `StatementHandlerThread` 에게 맡긴다.
  - 백업: ServerApp01.java

### 2단계 - `StatementHandlerThread` 를 `ServerApp` 의 스태틱 중첩 클래스로 변경한다.

- `com.eomcs.pms.ServerApp` 클래스 변경
    - `StatementHandlerThread2` 스태틱 중첩 클래스를 정의한다.
  - 백업: ServerApp02.java

### 3단계 - `StatementHandlerThread` 를 `ServerApp` 의 inner 클래스로 만든다.

- `com.eomcs.pms.ServerApp` 클래스 변경
  - `StatementHandlerThread3` inner 클래스를 정의한다.
  - 스레드에서 사용하는 메서드를 밖깥 클래스의 멤버로 옮긴다.
  - 왜? 코드를 읽기 쉽도록 하기 위함이다.
  - 코드가 여러 블록에 중접되면 될 수록 들여쓰기 하면서
    코드를 읽기가 불편해진다.
  - 백업: ServerApp03.java


### 4단계 - `StatementHandlerThread` 를 `ServerApp.service()` 에서 로컬 클래스로 만든다.

- `com.eomcs.pms.ServerApp` 클래스 변경
  - `StatementHandlerThread4` 로컬 클래스를 정의한다.
  - 백업: ServerApp04.java


### 5단계 - `StatementHandlerThread` 를 `ServerApp.service()` 에서 익명 클래스로 만든다.
- `com.eomcs.pms.ServerApp` 클래스 변경
  - 익명 클래스를 사용하면 해당 클래스가 어떻게 정의되어 있는지 가까이에서 바로 알 수 있기 때문에 유지보수에 좋다.
  - 백업: ServerApp05.java


### 6단계 - `StatementHandlerThread` 를 Runnable 구현체로 만든다.
- `com.eomcs.pms.ServerApp` 클래스 변경
  - 직접 스레드를 상속 받는 대신에 Runnable 인터페이스의 구현체로 만든다.
  - 백업: ServerApp06.java


### 7단계 - Runnnable 구현체를 람다(lambda) 문법으로 정의한다.

- `com.eomcs.pms.ServerApp` 클래스 변경
  - Runnable 인터페이스는 한 개의 추상 메서드를 가진 functional interface이다.
  - 이 경우 람다 문법으로 표현할 수 있다.


## 실습 결과
- src/main/java/com/eomcs/pms/StatementHandlerThread.java 추가
- src/main/java/com/eomcs/pms/ServerApp.java 변경
