# 34-d. Spring WebMVC 프레임워크 도입하기 : 요청 핸들러의 파라미터 다루기


이번 훈련에서는,
- **Spring WebMVC 프레임워크** 를 사용하는 방법을 배울 것이다.

## 훈련 목표
- 
 
## 훈련 내용
-

## 실습 

### 1단계: 요청 핸들러의 파라미터를 선언하는 방법

프론트 컨트롤러(`DispatcherServlet`)가 페이지 컨트롤러의 요청 핸들러를 호출할 때
    - 요청 핸들러의 파라미터를 분석하여 원하는 대로 값을 넘겨준다.
    - 요청 핸들러의 리턴 값을 분석하여 그에 따라 처리한다.
    - 물론 스프링에서 정한 규칙에 따라 파라미터나 리턴 타입을 선언해야 한다.
    
- 사용하지 않는 파라미터는 선언하지 않는다.
- 필요한 파라미터가 있다면 프론트 컨트롤러가 넘겨 줄 수 있는 범위 내에서 요청하라.
- 클라이언트가 보낸 요청 파라미터를 받고 싶다면 요청 파라미터 이름으로 핸들러의 파라미터를 선언하라.
- 클라이언트가 보낸 요청 파라미터를 도메인 객체로 직접 받고 싶다면 요청 핸들러의 파라미터에 도메인 타입의 파라미터를 선언하라.
- 뷰 컴포넌트(JSP)에게 전달할 값이 있다면 HttpServletRequest 대신 Map 또는 Model 객체를 달라고 해서 거기에 저장하라.
- 멀티파트 콘텐트를 처리할 MultipartResolver를 추가한다.
- 요청 파라미터 값이 넘어오지 않을 경우 다른 타입으로 변환할 때 예외가 발생한다. 
    - 이를 해결하기 위해서는 요청 핸들러의 파라미터 선언시 기본 값을 설정해야 한다.
- 객체 안에 객체를 포함할 경우 요청 파라미터의 이름을 OGNL 방식으로 표현한다.
    - com.eomcs.pms.web.BoardController 변경
    - com.eomcs.pms.web.MemberController 변경
    - com.eomcs.pms.web.ProjectController 변경
    - com.eomcs.pms.web.TaskController 변경
    - com.eomcs.pms.web.AuthController 변경

## 실습 결과
