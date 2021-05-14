# 31-f. Servlet 기술 활용하기 : 서블릿 예외 처리

이번 훈련에서는,
- **배치 파일(web.xml)** 을 이용하여 **예외 처리** 를 설정하는 방법을 배운다.

## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - web.xml 에 예외 설정을 추가한다.

- src/main/webapp/WEB-INF/web.xml 변경
  - 특정 예외 클래스에 대해 어떤 서블릿을 실행할 것인지 설정한다.
```
  <error-page>
    <exception-type>javax.servlet.ServletException</exception-type>
    <location>/error</location>
  </error-page>    
```

- com.eomcs.pms.web.ErrorHandler 변경
  - web.xml 배치한 경우 예외 객체를 꺼낼 때는 서블릿 명세에 따라 꺼내야 한다.



### 2단계 - 서블릿 실행 중에 특정 응답 코드에 해당하는 예외가 발생했을 때 포워딩 할 URL 설정한다.

- src/main/webapp/WEB-INF/web.xml 변경
  - 클라이언트가 요청한 URL이 무효할 때 404 예외 코드의 오류가 발생한다.
  - 이때 포워딩 할 페이지의 URL을 설정한다. 
- src/main/webapp/error/error404.html 추가

```
  <error-page>
    <error-code>404</error-code>
    <location>/error/error404.html</location>
  </error-page>     
```
 
## 실습 결과
- src/main/java/com/eomcs/pms/web/ErrorHandler.java 변경
- src/main/java/com/eomcs/pms/web/XxxHandler.java 변경
- src/main/webapp/WEB-INF/web.xml 변경
- src/main/webapp/error/error404.html 추가
