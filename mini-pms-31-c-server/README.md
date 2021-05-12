# 31-c. Servlet 기술 활용하기 : 리프래시와 리다이렉트 활용 

이번 훈련에서는,
- **리프래시(refresh)** 와 **리다이렉트(redirect)** 를 활용하는 방법을 배울 것이다.  

## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - 응답 헤더를 이용하여 클라이언트에게 Refresh 정보 보낸다.

- com.eomcs.pms.web.MemberAddHandler 변경
- com.eomcs.pms.web.MemberUpdateHandler 변경
- com.eomcs.pms.web.MemberDeleteHandler 변경
  - HTML의 meta 태그를 활용하여 리프래시를 설정하던 방식을 응답 헤더 방식으로 변경한다.

### 2단계 - 자동으로 다시 요청하게 할 때 리프래시 대신 리다이렉트를 사용한다.

- com.eomcs.pms.web.LoginHandler 변경
- com.eomcs.pms.web.LogoutHandler 변경
- com.eomcs.pms.web.ProjectAddHandler 변경
- com.eomcs.pms.web.ProjectUpdateHandler 변경
- com.eomcs.pms.web.ProjectDeleteHandler 변경
- com.eomcs.pms.web.TaskAddHandler 변경
- com.eomcs.pms.web.TaskUpdateHandler 변경
- com.eomcs.pms.web.TaskDeleteHandler 변경
  - HTML의 meta 태그를 활용하여 리프래시를 설정하던 방식을 리다이렉트 방식으로 변경한다.
 
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
