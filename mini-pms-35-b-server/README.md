# 35-b. 기타 : 페이징 처리


이번 훈련에서는,
- 목록을 출력할 때 **페이징 처리** 를 적용 해 볼 것이다.

## 훈련 목표
- 
 
## 훈련 내용
-

## 실습 

### 1단계: 데이터 목록을 가져오는 SQL에 페이징을 적용한다.

- src/main/resources/com/eomcs/pms/mapper/BoardMapper.xml 변경
    - `findByKeyword` SQL 변경 
    - `countByKeyword` SQL 추가

### 2단계: SQL 매퍼의 변경에 맞춰 DAO 메서드를 변경한다.

- com.eomcs.pms.dao.BoardDao 변경
    - `findByKeyword()` 메서드 변경
    - `countByKeyword()` 메서드 추가

### 3단계: DAO 변경에 맞처 Service 의 메서드를 변경한다.

- com.eomcs.pms.service.BoardService 변경
    - list(), search() 변경
    - `count()` 메서드 추가
- com.eomcs.pms.service.impl.BoardServiceImpl 변경

### 4단계: 화면에 출력할 목록 데이터를 준비하는 페이지 컨트롤러를 변경한다.

- com.eomcs.pms.web.BoardController 변경

### 5단계: 화면에 페이지를 지정하는 링크를 추가한다.

- src/main/webapp/WEB-INF/jsp/board/list.jsp 변경

## 실습 결과

