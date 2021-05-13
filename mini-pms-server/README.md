# 31-d. Servlet 기술 활용하기 : 포워딩과 인클루딩 활용 

이번 훈련에서는,
- **포워딩(forwarding)** 과 **인클루딩(including)** 을 활용하는 방법을 배울 것이다.  

## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - 포워딩을 이용하여 요청 처리를 다른 서블릿에게 위임한다.

- com.eomcs.pms.web.ErrorHandler 추가
  - 서블릿 실행 중 오류가 발생했을 때 실행되는 서블릿이다.
  - 간단한 오류 메시지와 상세 내용을 출력한다. 
- com.eomcs.pms.web.XxxHandler 변경
  - 예외가 발생했을 때 ErrorHandler로 포워딩 한다.

### 2단계 - 인클루딩을 이용하여 프로젝트 멤버 목록을 출력하는 기능을 별도의 서블릿으로 분리한다.

- com.eomcs.pms.web.ProjectMemberListHandler 추가
  - 프로젝트의 멤버를 선택하거나 출력하는 기능을 제공한다.
- com.eomcs.pms.web.ProjectAddHandler 변경
- com.eomcs.pms.web.ProjectDetailHandler 변경
  - 프로젝트 멤버 목록을 출력할 때 ProjectMemberListHandler를 인클루딩 한다.
 
## 실습 결과
- src/main/java/com/eomcs/pms/web/MemberAddHandler.java 변경
- src/main/java/com/eomcs/pms/web/MemberUpdateHandler.java 변경
- src/main/java/com/eomcs/pms/web/MemberDeleteHandler.java 변경
- src/main/java/com/eomcs/pms/web/LoginHandler.java 변경
- src/main/java/com/eomcs/pms/web/LogoutHandler.java 변경
- src/main/java/com/eomcs/pms/web/ProjectAddHandler.java 변경
- src/main/java/com/eomcs/pms/web/ProjectUpdateHandler.java 변경
- src/main/java/com/eomcs/pms/web/ProjectDeleteHandler.java 변경
- src/main/java/com/eomcs/pms/web/TaskAddHandler.java 변경
- src/main/java/com/eomcs/pms/web/TaskUpdateHandler.java 변경
- src/main/java/com/eomcs/pms/web/TaskDeleteHandler.java 변경
