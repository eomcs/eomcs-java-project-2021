# 34-e. Spring WebMVC 프레임워크 도입하기 : InternalResourceViewResolver 도입


이번 훈련에서는,
- **Spring WebMVC 프레임워크** 를 사용하는 방법을 배울 것이다.

## 훈련 목표
- 
 
## 훈련 내용
-

## 실습 

### 1단계: JSP를 /WEB-INF 폴더로 옮긴다.

- /WEB-INF 폴더는 클라이언트에서 접근할 수 없다.
    - 그래서 클라이언트에게 노출하지 않을 자원은 이 폴더에 둔다.
    - JSP 파일도 클라이언트에서 직접 요청하지 못하도록 이 폴더로 옮긴다.
    - 단 /WEB-INF 폴더 아래에 있는 JSP를 찾아 실행해 주는 ViewResolver를 빈 컨테이너에 등록해야 한다.
    - com.eomcs.pms.config.AppConfig 변경

### 2단계: 요청 핸들러의 JSP URL을 생략한다.
- 페이지 컨트롤러의 요청 핸들러를 변경한다.
    - InternalResourceViewResolver는 요청 핸들러로부터 JSP 주소를 리턴받지 못하면 요청 핸들러의 URL을 JSP 주소로 사용하여 찾는다.

## 실습 결과

