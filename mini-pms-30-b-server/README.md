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
- 톰캣 서버의 환경 변수 설정(예: ~/.zshrc)
  - `export CATALINA_HOME=톰캣설치경로` 설정(unix)
  - `export PATH=$CATALINA_HOME/bin:$JAVA_HOME/bin:기타경로:$PATH` 설정(unix)
- 톰캣 서버 실행 확인
  - $CATALINA_HOME/bin/startup.bat 파일 실행(Windows)
  - $CATALINA_HOME/bin/startup.sh 파일 실행(macOS/Linux/Unix)
    - 쉘스크립트 파일을 실행하려면 실행 권한을 부여해야 한다.
    - `$CATALINA_HOME/bin> chmod 755 *.sh` 명령을 실행한다.
  - 웹 브라우저에서 주소 창에 `http://localhost:8080` 입력하여 페이지 확인한다.
- 톰캣 서버 관리자 등록
  - $CATALINA_HOME/conf/tomcat-users.xml 에 관리자 정보 등록
  - 톰캣 서버 홈 화면의 `Manager App` 버튼과 `Host Manager` 버튼을 클릭할 때 나오는 안내 메시지에 따라 설정하라.
  - 즉 다음 코드를 추가하라.

```
<role rolename="manager-gui"/>
<role rolename="admin-gui"/>
<user username="tomcat" password="1111" roles="manager-gui,admin-gui"/>
```

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
  - 기존 코드를 `javax.servlet.Servlet` 인터페이스 규칙에 따라 동작하도록 변경한다.


### 4단계 - 톰캣 서버에서 실행할 수 있도록 배치한다.

- `$ gradle build`를 실행하여 `.war` 파일을 생성한다.
  - `.war` 파일은 `$프로젝트/app/build/libs/` 디렉토리에 있다. 
  - 명시적으로 `.war` 파일의 이름을 설정하지 않으면 폴더 이름(예: app)으로 생성된다.
- `.war` 파일명을 변경하려면,
  - `gradle.build` 파일에 `.war` 파일의 이름을 설정한다.
  - 예: `war { archiveBaseName="pms"}` 설정을 추가한다.
  - `$ gradle build`를 재실행한다.
    - 기존에 생성된 파일을 삭제하려면 `$ gradle clean` 을 실행하라. 
- `.war` 파일을 `$CATALINA_HOME/webapps/` 폴더로 복사한다.


### 5단계 - 톰캣 서버를 재실행하여 `.war` 파일에 패킹된 애플리케이션을 실행한다.

- 웹 브라우저에서 다음의 주소를 입력하여 `HelloHandler` 클래스를 실행한다.
  - `localhost:8080/프로젝트명(war 파일명)/hello`


## 실습 결과

- build.gradle 변경
- src/main/java/com/eomcs/pms/web/HelloHandler.java 추가
- src/main/java/com/eomcs/pms/ServerApp.java 삭제

