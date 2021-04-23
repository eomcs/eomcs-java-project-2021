# 30-b. 웹 애플리케이션 서버 아키텍처로 전환하기 : `ServerApp`을 톰캣 서버로 대체하기 

이번 훈련에서는,
- **JavaEE** 의 **Servlet/JSP** 기술을 이용하여 기존의 애플리케이션 서버 아키텍처를 웹 애플리케이션 서버(Web Application Server: WAS) 아키텍처로 전환할 것이다.  

## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - 웹 애플리케이션 서버를 설치한다.

- 톰캣 서버 설치
  - tomcat.apache.org 사이트에서 다운로드 한다.
  - `~/server/` 디렉토리에 압축을 푼다.
- 톰캣 서버 실행 확인
  - $CATALINA_HOME/bin/startup.bat 파일 실행(Windows)
  - $CATALINA_HOME/bin/startup.sh 파일 실행(macOS/Linux/Unix)
  - 웹 브라우저에서 주소 창에 `http://localhost:8080` 입력하여 페이지 확인한다.

### 2단계 - 웹 애플리케이션 제작에 필요한 라이브러리와 그래이들 플러그인을 설정한다.

- build.gradle 변경
  - `war` 그래이들 플러그인 추가
    - 웹 애플리케이션 패키지 파일(.war)을 만들어 주는 플러그인이다.
  - 웹 애플리케이션 제작에 필요한 라이브러리 추가
    - http://search.maven.org 사이트에 간다.
    - `javax.servlet-api` 키워드로 검색한다.
    - `4.0.1` 버전의 라이브러리 정보를 가져온다.
  - `$ gradle eclipse` 실행한다.
    - 의존 라이브러리도 가져온다.

### 3단계 - 기존의 `HelloHandler`를 Servlet/JSP 규칙에 맞춰 변경한다.

- com.eomcs.pms.web.HelloHandler 클래스 추가
  - 웹 관련 자바 소스 파일을 따로 관리하기 위해 web 패키지를 만든다.
  - 기존의 `HelloHandler`를 복사해 온다.
  - 기존 코드를 `Servlet` 인터페이스 규칙에 따라 동작하도록 변경한다.

### 4단계 - 웹 애플리케이션 배치 설정 파일을 추가한다.

- `$프로젝트폴더/app/src/main/webapp/WEB-INF` 폴더를 생성한다.
- `$$CATALINA_HOME/webapps/examples/WEB-INF/web.xml` 파일을 복사해서 이 폴더에 둔다.
- 최소 사항으로 설정한다. 

### 5단계 - 톰캣 서버에서 실행할 수 있도록 배치한다.

- `$ gradle build`를 실행하여 `.war` 파일을 생성한다.
  - `.war` 파일은 `$프로젝트/app/build/libs/` 디렉토리에 있다. 
- `.war` 파일을 `$CATALINA_HOME/webapps/` 폴더로 복사한다.

### 6단계 - 톰캣 서버를 재실행하여 `.war` 파일에 패킹된 애플리케이션을 실행한다.

- 웹 브라우저에서 다음의 주소를 입력하여 `BoardListHandler` 클래스를 실행한다.
  - `localhost:8080/프로젝트명(war 파일명)/board/list`


### 2단계 - 기존 서버 프로젝트를 웹 프로젝트로 전환한다.

- build.gradle 변경
  - 그래이들 플러그인 추가
    - 기존의 `eclipse` 플러그인 대신에 `eclipse-wtp` 을 사용한다.
    - `war` 플러그인 추가
    - 이 두 개의 플러그인이 있어야만 웹 프로젝트에 관련된 설정 파일을 생성할 수 있다.
  - 웹 프로젝트에 필요한 라이브러리 추가
    - http://search.maven.org 사이트에 간다.
    - `javax.servlet-api` 키워드로 검색한다.
    - `4.0.1` 버전의 라이브러리 정보를 가져온다.
  - `$ gradle eclipse` 실행한다.
    - 이클립스 웹 프로젝트와 관련된 설정 파일을 생성한다.
    - 의존 라이브러리도 가져온다.
  - 이클립스 IDE 에서 프로젝트를 refresh 한다.

### 2단계 - 서버 프로젝트에 Servlet/JSP 프로그래밍에 필요한 라이브러리를 가져온다.

### 2단계 - 톰캣 서버의 위치를 이클립스에 등록한다.

- 이클립스 메뉴 > Window >  Preferences...
  - Server > Runtime Environments > add 버튼 클릭
  - 톰캣 서버의 디렉토를 등록한다.

### 3단계 - 개발할 때 사용할 톰캣 서버 실행 환경을 구축한다.

- 이클립스 > Servers 뷰 > 새 서버 실행 환경 등록





### 5단계 - 웹 프로젝트를 톰캣 실행 환경에 등록한다.

- 이클립스 > Servers 뷰 > 서버 실행 환경 > add
  - 프로젝트를 추가한다.


## 실습 결과

- src/main/java/com/eomcs/pms/ServerApp.java 변경

