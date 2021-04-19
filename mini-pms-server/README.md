# 27-d. 애플리케이션을 클라이언트/서버 구조로 변경하기 : PMS 코드를 C/S로 분리

이번 훈련에서는,
- 기존의 **PMS 코드** 를 클라이언트/서버로 분리할 것이다.


## 훈련 목표
- 

## 훈련 내용
- 

## 실습

- **mini-pms-26-c-server** 프로젝트의 소스 파일을 가지고 작업한다.


### 1단계 - 프로젝트에 라이브러리 추가하기   

- build.gradle 변경
  - MariaDB JDBC 드라이버 추가 
  - Mybatis 라이브러리 추가
  - `$ gradle eclipse` 실행
  - 이클립스에서 프로젝트 갱신

### 2단계 - Mybatis 설정 파일 및 SQL 매퍼 파일을 가져온다.  

- `mini-pms-26-c-client/app/src/main/resources` 폴더를 가져온다.
  - `src/main/resoures/com/eomcs/pms/conf/*` 폴더 및 파일 복사.
  - `src/main/resoures/com/eomcs/pms/mapper/*` 폴더 및 파일 복사.

### 3단계 - 도메인 클래스를 가져온다.

- `mini-pms-26-c-client/app/src/main/com/eomcs/pms/domain` 패키지를 가져온다.

### 4단계 - DAO 인터페이스를 가져온다.

- `mini-pms-26-c-client/app/src/main/com/eomcs/pms/dao` 패키지를 가져온다.

### 5단계 - 서비스 인터페이스와 구현체를 가져온다.

- `mini-pms-26-c-client/app/src/main/com/eomcs/pms/service` 패키지를 가져온다.

### 6단계 - @Component 애노테이션 관련 클래스나 패키지를 가져온다.

- `mini-pms-26-c-client/app/src/main/com/eomcs/stereotype` 패키지를 가져온다.

### 7단계 - Command 구현체를 가져온다.

- `mini-pms-26-c-client/app/src/main/com/eomcs/pms/handler` 패키지를 가져온다.

### 8단계 - DAO 인터페이스 자동 생성과 관련된 클래스나 패키지를 가져온다.

- `mini-pms-26-c-client/app/src/main/com/eomcs/mybatis` 패키지를 가져온다.

### 9단계 - 기존의 `ClientApp` 클래스에 있는 코드를 `ServerApp` 으로 옮긴다.

- `com.eomcs.pms.ServerApp` 클래스 변경
  - `mini-pms-26-c-client/app/src/main/com/eomcs/pms/ClientApp` 클래스의 코드를 가져온다.
  - Mybatis 프레임워크와 관련된 코드를 옮긴다.
  - DAO 구현체를 자동으로 만들어주는 공장 객체 생성 코드를 옮긴다.
  - DAO 공장 객체를 사용해서 DAO 객체를 생성하는 코드를 옮긴다.
  - Service 객체를 생성하는 코드를 옮긴다.
  - Command 구현체를 자동 생성하는 코드를 옮긴다.

### 10단계 - 클라이언트 요청에 대해 Command 구현체를 실행하도록 코드를 변경한다.

- `com.eomcs.util.CommandRequest` 클래스 생성
  - 클라이언트 요청 정보를 다루는 객체를 정의한다.
- `com.eomcs.util.CommandResponse` 클래스 생성
  - 클라이언트 응답 정보를 다루는 객체를 정의한다.
- `com.eomcs.pms.handler.Command` 인터페이스 변경
  - `service()` 를 `service(CommandRequest, CommandResponse)` 로 변경한다.
- `com.eomcs.pms.ServerApp` 클래스 변경
  - 클라이언트 요청이 들어 왔을 때 해당 요청을 처리할 Command 객체를 찾아 실행한다.


## 실습 결과
- src/main/java/com/eomcs/pms/ServerApp.java 변경


