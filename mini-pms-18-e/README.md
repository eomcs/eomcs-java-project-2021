# 18-e. 바이트 스트림 다루기 : 리팩터링 I

이번 훈련에서는 **리팩터링** 활동을 연습할 것이다.

**리팩터링(refactoring)** 은, (마틴 파울러의 '리팩터링'에서 발췌)
- 소프트웨어를 보다 쉽게 이해할 수 있고,
  적은 비용으로 수정할 수 있도록
  겉으로 보이는 동작의 변화 없이 내부 구조를 변경하는 것.
- 중복된 코드를 제거하여 설계(design)를 개선시킨다.
- 코드를 더 이해하기 쉽게 만든다.
- 버그를 찾기 쉽게 해준다.
- 프로그램을 빨리 작성하도록 도와준다.

**리팩터링** 을 해야할 때,
- 비슷한 코드를 중복해서 작성할 때
- 기능을 추가할 때
- 버그를 수정할 때
- 코드 리뷰(code review)를 수행할 때

## 훈련 목표

- **리팩터링**이 필요한 시점과 목적을 이해한다.
- 메서드 파라미터에 제네릭을 적용하는 것을 연습한다.

## 훈련 내용

- 각 도메인 객체 별로 데이터를 저장하고 읽는 로직을 하나로 통합한다.
- 다양한 타입의 객체 목록을 다룰 수 있도록 파라미터에 제네릭을 적용한다.

## 실습

### 1단계 - `saveXxx()`, `loadXxx()` 메서드에서 사용하는 파일명을 스태틱 필드로 만든다.

- 파일명을 가지고 File 객체를 생성한다.

### 작업 파일

- com.eomcs.pms.App 변경
    - 백업: App01.java

### 2단계 - `saveXxx()` 메서드들을 `saveObjects()` 메서드로 통합한다.

- App 클래스
  - `saveBoards()`, `saveMembers()`, `saveProjects()`, `saveTasks()` 메서드를 `saveObjects()` 메서드로 통합한다.

#### 작업 파일

- com.eomcs.pms.App 변경
    - 백업: App02.java


### 3단계 - `loadXxx()` 메서드들을 `loadObjects()` 메서드로 통합한다.

- App 클래스
  - `loadBoards()`, `loadMembers()`, `loadProjects()`, `loadTasks()` 메서드를 `loadObjects()` 메서드로 통합한다.

#### 작업 파일

- com.eomcs.pms.App 변경

## 실습 결과

- src/main/java/com/eomcs/pms/App.java 변경
