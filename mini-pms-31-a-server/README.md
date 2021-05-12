# 31-a. Servlet 기술 활용하기 : GET/POST 요청 구분과 HTML 도입하기 

이번 훈련에서는,
- **HTML** 웹 기술을 이용하여 사용자 UI를 다루는 방법을 배울 것이다.  

## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - 게시판 콘텐트의 출력 형식을 HTML로 바꾼다.

- com.eomcs.pms.web.BoardListHandler 변경
- com.eomcs.pms.web.BoardDetailHandler 변경
- src/main/webapp 생성
- src/main/webapp/board/form.html 생성
  - `$ gradle eclipse`를 다시 실행하여 이클립스 설정 파일을 갱신하라.
  - 그래야만 src/main 에 추가한 webapp 폴더를 이클립스가 인식할 수 있다.
- com.eomcs.pms.web.BoardAddHandler 변경
- com.eomcs.pms.web.BoardUpdateHandler 변경
- com.eomcs.pms.web.BoardDeleteHandler 변경
- com.eomcs.pms.web.BoardSearchHandler 변경

### 2단계 - 회원, 프로젝트, 작업 콘텐트의 출력 형식을 HTML로 바꾼다.

- com.eomcs.pms.web.ProjectListHandler 변경
 
## 실습 결과
- build.gradle 변경
