# 32-a. MVC 아키텍처로 전환하기 : JSP를 활용하여 view 컴포넌트 분리하기

이번 훈련에서는,
- **JSP(Java Server Page)** 템플릿 엔진 기술을 활용하여 UI 출력 코드를 자동으로 생성하는 방법을 배운다.

## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - 게시글 관리 서블릿에서 출력 부문을 JSP로 추출한다.

- 게시글 목록 조회에 JSP 적용하기
  - src/main/webapp/jsp/board/list.jsp 추가
  - com.eomcs.pms.web.BoardListHandler 변경
 
## 실습 결과
- src/main/java/com/eomcs/pms/web/ErrorHandler.java 변경
- src/main/java/com/eomcs/pms/web/XxxHandler.java 변경
- src/main/webapp/WEB-INF/web.xml 변경
- src/main/webapp/error/error404.html 추가
