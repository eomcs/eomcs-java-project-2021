# 32-b. MVC 아키텍처로 전환하기 : JSP 엘리먼트와 JSP 액션 태그 사용하기

이번 훈련에서는,
- **JSP(Java Server Page)** 템플릿 엔진 기술을 활용하여 UI 출력 코드를 자동으로 생성하는 방법을 배운다.

## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - JSP 액션 태그를 사용하여 자바 코드 직접 작성을 줄인다.

- <jsp:useBean/> 태그 사용
- <jsp:include/> 태그 사용
  - /src/main/webapp/jsp/**/*.jsp 변경

### 2단계 - JSP 선언 엘리먼트를 사용하여 JSP에서 사용할 메서드를 정의한다.

- <%! 메서드 및 필드 선언 %> declaration element 사용
  - /src/main/webapp/jsp/project/member_list.jsp 변경
 
## 실습 결과
- src/main/java/com/eomcs/pms/web/XxxHandler.java 변경
- src/main/webapp/jsp/**/*.jsp 추가
