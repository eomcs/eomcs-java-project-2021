# 26-c. 객체 생성을 자동화하기 : 애노테이션을 이용한 Command 구현체 자동 생성


## 훈련 목표
- Reflection API의 사용법을 배운다.

## 훈련 내용
- Reflection API를 사용하여 클래스를 정보를 알아낸 후 인스턴스를 자동생성한다.

## 실습

### 1단계: Command 구현체의 정보를 담을 애노테이션을 정의한다.

- src/main/resources/com/eomcs/stereotype/Component 애노테이션 정의 

### 2단계: Command 구현체에 `@Componennt` 애노테이션을 붙인다.

- com.eomcs.pms.handler.XxxHandler 클래스 변경
  - 클래스 선언부에 `@Component` 애노테이션을 붙인다.
  - `@Component` 애노테이션의 value 프로퍼티에 명령을 지정한다.
- src/main/resources/com/eomcs/pms/conf/commands.properties 파일 삭제
  - 이 프로퍼티 파일에 적어 두었던 내용은 `@Component` 애노테이션으로 대체했기 때문이 이제 더이상 필요없다.

### 3단계: 특정 패키지에 들어 있는 클래스 이름을 알아낸 후 Command 구현체를 생성한다.

- com.eomcs.ClientApp 클래스 변경
  - 

## 실습 결과
- src/main/resources/com/eomcs/pms/conf/commands.properties 추가
- src/main/java/com/eomcs/pms/ClientApp.java 변경

