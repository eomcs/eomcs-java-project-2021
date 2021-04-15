# eomcs-java-project-2021

이 저장소는 '[엄진영의 코딩스쿨] 프로젝트로 배우는 자바 실전 프로그래밍'의 강의 예제 소스를 보관하는 곳이다.

이 강의의 핵심 목표는 개발 입문자에게 `자바 프로그래밍의 실전 기법`과 `개발 경험`을 전달하는 것이다.
이를 위해 우리는 간단한 프로젝트를 진행할 것이다.
이 프로젝트를 통해 자바 문법이 어떻게 실전에서 활용되는지 배울 수 있고,
과거에서 최근까지 약 20여년에 걸쳐 애플리케이션 아키텍처가 진화해 온 과정을 압축하여 경험할 수 있다.

예제로 진행할 프로젝트는 소규모 팀을 위한 '프로젝트 관리 시스템'을 만드는 것이다.
콘솔 입출력에서 시작하여 웹 애플리케이션, 모바일 웹까지 단계적으로 진화하도록 구성하였다.
각 단계마다 구현 목표가 있으며, 구현에 필요한 자바 문법과 기법들을 소개하였다.

프로젝트의 각 단계를 따라가다 보면,
`자료구조`에 따라 데이터를 다루는 방법이나
`리팩터링`을 통해 코드를 유지보수 하기 좋게 만드는 방법,
`GoF의 디자인 패턴`으로 기능 변경이나 확장이 용이한 구조로 애플리케이션을 설계하는 방법 등을 배울 수 있다.

특히 단계적으로 기술이 진화해 가는 과정을 체험함으로써,
단순한 프로그래밍 역량 강화를 넘어서 더 빠르게 성장할 수 있는 토대를 마련할 것이다.
당장 시스템 유지보수에 투입될 예정인 신입 개발자라면,
앞으로 마주하게 될 다양한 구조의 시스템에 대한 대응력을 더 높이는 계기가 될 것이다.

## 대상자

- 자바 기본 문법을 공부중인 분
- 서블릿/JSP를 학습하였거나 학습하려는 분
- C/C++, Python 등 다른 프로그래밍 언어를 알고 있는 데, 자바 프로그래밍을 빠르게 배우고 싶은 분
- 자바 기본 문법을 공부하였는데 어떻게 응용해야 할 지 모르겠는 분
- 다양한 오픈 소스를 자바 애플리케이션에 개발에 적용하는 방법을 배우고 싶은 분
- 스프링 프레임워크 기반 프로젝트에 참여중이거나 참여할 예정인 분
- 자바 웹 애플리케이션 프로젝트의 유지보수를 맡고 있거나 맡을 예정인 분
- 웹 애플리케이션의 아키텍처나 스프링 프레임워크의 내부 구조가 궁금한 분

## 학습 목표

이 교육과정을 통해 다음을 배울 수 있다.  

- 자바 언어에서 제공하는 각종 문법의 목적을 이해하고 활용하는 방법
- 기본적인 자료구조를 구현하고 활용하는 방법
- 리팩터링과 디자인 패턴을 적용하는 방법
- 스프링 프레임워크, 마이바티스 등 오픈 소스 프레임워크를 프로젝트에 적용하는 방법
- 애플리케이션 아키텍처의 발전 과정을 이해하고 구현하는 방법

## 프로젝트 버전 및 학습 목표

### 개발 도구 준비하기

- GraalVM(OpenJDK 11 포함) 설치 및 환경 설정
- Eclipse 설치 및 환경 설정
- Visual Studio Code 설치 및 환경 설정
- Scoop(Win)/Homebrew(macOS) 패키지 관리자 설치
- Gradle 빌드 도구 설치
- Git 형상관리 도구 설치
- MariaDB 데이터베이스 설치

### 강의 자료 가져오기

- `github.com` 사이트에 가입하기
- `github.com/eomcs/eomcs-java-project-2020` 저장소를 로컬(개발 PC)로 복제하기

### 개인 저장소 만들기

- github.com 에 개인 저장소 만들기
- github.com 의 개인 저장소를 로컬로 복제하기

### 01-a. 프로젝트 준비하기 : Gradle 빌드 도구를 활용한 자바 프로젝트 구성
### 01-b. 프로젝트 준비하기 : `이클립스 IDE`로 임포트
### 01-c. 프로젝트 준비하기 : 버전 관리 시스템 Git 적용

### 02-a. 값 다루기 : 리터럴과 콘솔 출력
### 02-b. 값 다루기 : 변수와 키보드 입력
### 02-c. 값 다루기 : 배열과 흐름 제어문 활용

### 03-a. 메서드 사용법 : 프로그램의 시작점(entry point), `main()`
### 03-b. 메서드 사용법 : 메서드 활용

### 04-a. 클래스 사용법 : 메서드 분류
### 04-b. 클래스 사용법 : 새 데이터 타입 정의  
### 04-c. 클래스 사용법 : 패키지로 클래스 분류
### 04-d. 클래스 사용법 : 의존 관계

### 05-a. 인스턴스 사용법 : 클래스 필드와 클래스 메서드의 한계
### 05-b. 인스턴스 사용법 : 인스턴스 필드가 필요한 이유와 사용법
### 05-c. 인스턴스 사용법 : GRASP의 Information Expert 디자인 패턴 적용
### 05-d. 인스턴스 사용법 : 인스턴스 메서드가 필요한 이유와 사용법

### 06-a. 의존 객체 다루기 : 인스턴스 필드에 직접 주입
### 06-b. 의존 객체 다루기 : 생성자로 주입

### 07. CRUD 구현하기
### 07-x. CRUD 구현하기 : 메뉴 출력과 역할 분담

### 08-a. 자료 구조 다루기 I : 배열 크기 변경
### 08-b. 자료 구조 다루기 I : 연결리스트 적용

### 09-a. 자료 구조 다루기 II : 데이터 처리 코드를 캡슐화
### 09-b. 자료 구조 다루기 II : 데이터 처리 코드를 캡슐화 + 배열 크기 변경
### 09-c. 자료 구조 다루기 II : 데이터 처리 코드를 캡슐화 + 연결리스트 적용

### 10. 멤버의 접근 제어 다루기 : 세터(setter)와 게터(getter)

### 11-a. 다형성 다루기 : 다형적 변수와 형변환
### 11-b. 다형성 다루기 : 오버라이딩과 오버로딩

### 12-a. 자료 구조 다루기 III : 스택 구현과 사용
### 12-b. 자료 구조 다루기 III : 큐 구현과 사용

### 13-a. `Iterator` 디자인 패턴 : 데이터 조회 기능을 캡슐화
### 13-b. `Iterator` 디자인 패턴 : XxxIterator 에 대해 Generalization 수행
### 13-c. `Iterator` 디자인 패턴 : Iterator 패턴에 인터페이스 문법 적용
### 13-d. `Iterator` 디자인 패턴 : GRASP의 Creator 디자인 패턴 적용
### 13-e. `Iterator` 디자인 패턴 : 스태틱 중첩 클래스(static nested class) 활용
### 13-f. `Iterator` 디자인 패턴 : 논스태틱 중첩 클래스(inner class) 활용
### 13-g. `Iterator` 디자인 패턴 : 로컬 클래스(local class) 활용
### 13-h. `Iterator` 디자인 패턴 : 익명 클래스(anonymous class) 활용

### 14. 제네릭이 필요한 이유와 사용법

### 15. 자바 컬렉션 API 사용하기

### 16. 예외가 발생했을 때 시스템을 멈추지 않게 하는 방법

### 17-a. `Command` 디자인 패턴 : 적용 전 문제점 확인
### 17-b. `Command` 디자인 패턴 : 메서드를 객체로 분리
### 17-c. `Command` 디자인 패턴 : 인터페이스로 객체의 사용 규칙 통일
### 17-d. `Command` 디자인 패턴 : `Map`으로 커맨드 객체 관리

### 18-a. 바이트 스트림 다루기 : 바이너리 형식의 데이터 입출력(FileInputStream/FileOutputStream)
### 18-b. 바이트 스트림 다루기 : 데코레이터 객체 활용(DataInputStream/DataOutputStream)
### 18-c. 바이트 스트림 다루기 : 버퍼 사용(BufferedInputStream/BufferedOutputStream)
### 18-d. 바이트 스트림 다루기 : 객체 입출력(ObjectInputStream/ObjectOutputStream)
### 18-e. 바이트 스트림 다루기 : 리팩터링 I

### 19-a. 문자 스트림 다루기 : 텍스트(CSV 파일 포맷) 입출력(FileReader/FileWriter)
### 19-b. 문자 스트림 다루기 : 버퍼 사용(BufferedReader/BufferedWriter)
### 19-c. 문자 스트림 다루기 : 리팩터링 I
### 19-d. 문자 스트림 다루기 : 리팩터링 II
### 19-e. 문자 스트림 다루기 : JSON 파일 포맷 입출력

### 20-a. `Observer` 디자인 패턴 : 프로젝트에 적용
### 20-b. `Observer` 디자인 패턴 : 파일 입출력 `Observer`

### 21-a. 데이터 관리 서버 만들기 : 클라이언트/서버 프로젝트 준비
### 21-b. 데이터 관리 서버 만들기 : 간단한 메시지 송수신
### 21-c. 데이터 관리 서버 만들기 : 사용자가 입력한 명령처리
### 21-d. 데이터 관리 서버 만들기 : 프로토콜 정의 및 적용
### 21-e. 데이터 관리 서버 만들기 : 파일 및 데이터 처리 기능을 서버로 이전
### 21-f. 데이터 관리 서버 만들기 : 드라이버 객체 도입
### 21-g. 데이터 관리 서버 만들기 : 다중 클라이언트의 동시 접속 처리

### 22-a. 데이터 관리를 DBMS에게 맡기기 : JDBC API 사용
### 22-b. 데이터 관리를 DBMS에게 맡기기 : SQL 삽입 공격과 자바 시큐어 코딩
### 22-c. 데이터 관리를 DBMS에게 맡기기 : 무결성 제약 조건 및 트랜잭션
### 22-d. 데이터 관리를 DBMS에게 맡기기 : 무결성 제약 조건 II

### 23-a. 데이터 처리 코드를 캡슐화하기 : DAO 클래스 도입
### 23-b. 데이터 처리 코드를 캡슐화하기 : DAO 인터페이스 도입
### 23-c. 데이터 처리 코드를 캡슐화하기 : 의존 객체 주입과 DB 커넥션 객체 공유
### 23-d. 데이터 처리 코드를 캡슐화하기 : 트랜잭션이 필요한 이유와 명시적인 rollback

### 24-a. DB 프로그래밍을 더 쉽고 간단히 하는 방법 : Mybatis 퍼시스턴스 프레임워크 도입
### 24-b. DB 프로그래밍을 더 쉽고 간단히 하는 방법 : Mybatis의 기타 기능 활용
### 24-c. DB 프로그래밍을 더 쉽고 간단히 하는 방법 : Mybatis의 트랜잭션 제어

### 25-a. 비즈니스 로직 분리하기 : DAO에서 트랜잭션을 다룰 때의 한계
### 25-b. 비즈니스 로직 분리하기 : 서비스 객체의 도입
### 25-c. 비즈니스 로직 분리하기 : 서비스 객체를 인터페이스와 구현체로 분리

### 26-a. 객체 생성을 자동화하기 : Java Proxy를 이용한 DAO 구현체 자동 생성
### 26-b. 객체 생성을 자동화하기 : .propertis 파일을 이용한 Command 구현체 자동 생성
### 26-c. 객체 생성을 자동화하기 : 애노테이션을 이용한 Command 구현체 자동 생성

### 35-a. 애플리케이션을 클라이언트/서버 구조로 변경하기 : Stateful 방식 통신
### 35-b. 애플리케이션을 클라이언트/서버 구조로 변경하기 : Stateless 방식 통신
### 35-c. 애플리케이션을 클라이언트/서버 구조로 변경하기 : 멀티스레드 적용
### 35-d. 애플리케이션을 클라이언트/서버 구조로 변경하기 : 스레드풀 구현하기
### 35-e. 애플리케이션을 클라이언트/서버 구조로 변경하기 : 자바에서 제공하는 스레드풀 사용하기
### 35-f. 애플리케이션을 클라이언트/서버 구조로 변경하기 : 멀티스레드 환경에서 Mybatis를 다루는 방법

### 36-a. 사용자 인증(authentication)하기 : 로그인/로그아웃 구현
### 36-b. 사용자 인증(authentication)하기 : 세션으로 사용자 구분

### 40-a. 커맨드 실행 전/후에 기능 추가하기: 디자인 패턴 적용 전
### 40-b. 커맨드 실행 전/후에 기능 추가하기: Chain of Responsibility 패턴 적용
### 40-c. 커맨드 실행 전/후에 기능 추가하기: init() 와 destroy()의 필요성

### 44. 웹 애플리케이션 서버(Web Application Server: WAS) 아키텍처로 전환하기 : Servlet 기술 도입


### 37 - 애플리케이션 서버가 등장한 이유!

### 39 - 여러 스레드가 동시에 같은 커넥션을 사용했을 때 발생되는 문제와 해결책

### 40 - 스레드 로컬 변수를 활용하여 작업 간에 DB 커넥션 공유하기

### 41 - 커넥션 재사용을 위해 커넥션풀 적용하기

### 46 - 객체 생성을 자동화하는 IoC 컨테이너 만들기

### 47 - IoC 컨테이너 개선 : 애노테이션을 이용한 객체 관리

### 48 - 인터페이스 대신 애노테이션으로 메서드 구분하기

### 49 - CRUD 메서드를 묶어 한 클래스로 분류하기

### 50 - Spring IoC 컨테이너 도입하기

### 51 - Spring IoC 컨테이너와 MyBatis 연동하기

### 52 - 애노테이션을 이용하여 트랜잭션 제어하기

### 53 - Log4j를 사용하여 애플리케이션 로그 처리하기

### 54 - Web 기술 도입하기
