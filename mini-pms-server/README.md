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

 
## 실습 결과
- src/main/java/com/eomcs/pms/web/filter/CharacterEncodingFilter.java 추가
- src/main/java/com/eomcs/pms/web/listener/ContextLoaderListener.java 추가
- src/main/java/com/eomcs/pms/web/AppInitHandler.java 변경
- src/main/webapp/WEB-INF/web.xml 변경
