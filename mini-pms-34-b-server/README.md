# 34-b. Spring WebMVC 프레임워크 도입하기 : CRUD 요청 핸들러 합치기


이번 훈련에서는,
- **Spring WebMVC 프레임워크** 를 사용하는 방법을 배울 것이다.

## 훈련 목표
- 
 
## 훈련 내용
-

## 실습 

### 1단계: CRUD 요청 핸들러를 하나의 페이지 컨트롤러로 합친다.

- com.eomcs.pms.web.BoardController 생성
    - com.eomcs.pms.web.BoardXxxHandler 의 기능을 이 클래스로 이전하고 기존 클래스를 삭제한다.
- com.eomcs.pms.web.MemberController 생성
    - com.eomcs.pms.web.MemberXxxHandler 의 기능을 이 클래스로 이전하고 기존 클래스를 삭제한다.
- com.eomcs.pms.web.ProjectController 생성
    - com.eomcs.pms.web.ProjectXxxHandler 의 기능을 이 클래스로 이전하고 기존 클래스를 삭제한다.
- com.eomcs.pms.web.TaskController 생성
    - com.eomcs.pms.web.TaskXxxHandler 의 기능을 이 클래스로 이전하고 기존 클래스를 삭제한다.
- com.eomcs.pms.web.AuthController 생성
    - com.eomcs.pms.web.LoginHandler 의 기능을 이 클래스로 이전하고 기존 클래스를 삭제한다.
    - com.eomcs.pms.web.LogoutHandler 의 기능을 이 클래스로 이전하고 기존 클래스를 삭제한다.
    - com.eomcs.pms.web.UserInfoHandler 의 기능을 이 클래스로 이전하고 기존 클래스를 삭제한다.
- com.eomcs.pms.web.ErrorController 생성
    - com.eomcs.pms.web.ErrorHandler 의 기능을 이 클래스로 이전하고 기존 클래스를 삭제한다.


## 실습 결과

