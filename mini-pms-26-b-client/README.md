# 26-b. 객체 생성을 자동화하기 : .propertis 파일을 이용한 Command 구현체 자동 생성


## 훈련 목표
- Reflection API의 사용법을 배운다.

## 훈련 내용
- Reflection API를 사용하여 클래스를 정보를 알아낸 후 인스턴스를 자동생성한다.

## 실습

### 1단계: Command 구현체 정보를 담은 .properties 파일을 생성한다.

- src/main/resources/com/eomcs/pms/conf/commands.properties 생성
  - 커맨드 구현체의 정보를 저장한다.
    - key : 사용자가 입력하는 명령어
    - value : 명령을 처리할 Command 구현체의 전체 이름

### 2단계: Command 구현체를 자동 생성한다.

- com.eomcs.ClientApp 클래스 변경
  - `commands.properties` 파일에 등록된 정보를 읽어서 `Command` 객체를 생성하여 객체 맵에 보관한다.
  - 사용자가 입력한 명령을 처리할 때 객체 맵에서 `Command` 구현체를 찾아 실행한다.

## 실습 결과
- src/main/resources/com/eomcs/pms/conf/commands.properties 추가
- src/main/java/com/eomcs/pms/ClientApp.java 변경

