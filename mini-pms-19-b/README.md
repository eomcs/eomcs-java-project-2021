# 19-b. 문자 스트림 다루기 : 버퍼 사용(BufferedReader/BufferedWriter)

이번 훈련에서는 **character stream class** 의 `BufferedReader/BufferedWriter` 를 활용하여 데이터 읽기, 쓰기 속도를 높이는 것을 연습할 것이다.

**BufferedReader/BufferedWriter** 는,
- 동작원리가 `BufferedInputStream/BufferedOutputStream` 와 같다.


## 훈련 목표

- `BufferedReader/BufferedWriter` 를 사용하는 이유와 동작 원리를 이해한다.

## 훈련 내용

- `FileReader/FileWriter` 에 `BufferedReader/BufferedWriter` 데코레이터를 붙여 읽기, 쓰기 속도를 높인다.


## 실습


### 1단계 - FileReader/FileWriter 에 BufferedReader/BufferedWriter 데코레이터를 연결한다.

- App 클래스
  - 기존의 `saveXxx()` 와 `loadXxx()`를 변경한다.

#### 작업 파일

- com.eomcs.pms.App 변경


## 실습 결과

- src/main/java/com/eomcs/pms/App.java 변경
