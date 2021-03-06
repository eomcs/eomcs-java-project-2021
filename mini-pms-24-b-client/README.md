# 24-b. DB 프로그래밍을 더 쉽고 간단히 하는 방법 : Mybatis의 기타 기능 활용

이번 훈련에서는,
- 실무에서 자주 쓰이는 *퍼시스턴스 프레임워크* 중에 하나인 **마이바티스** 프레임워크의 사용법을 배울 것이다.

**퍼시스턴스 프레임워크(Persistence Framework)** 는,
- 데이터의 저장, 조회, 변경, 삭제를 다루는 클래스 및 설정 파일들의 집합이다.(위키백과)
- JDBC 프로그래밍의 번거로움 없이 간결하게 데이터베이스와 연동할 수 있다.
- 소스 코드에서 SQL 문을 분리하여 관리한다.

**마이바티스(Mybatis)** 는,
- *퍼시스턴스 프레임워크* 중의 하나이다.
- JDBC 프로그래밍을 캡슐화하여 데이터베이스 연동을 쉽게 하도록 도와준다.
- 자바 소스 파일에서 SQL을 분리하여 별도의 파일로 관리하기 때문에
  자바 소스 코드를 간결하게 유지할 수 있다.
- JDBC 프로그래밍 할 때와 마찬가지로 직접 SQL을 다루기 때문에
  레거시(legacy) 시스템에서 사용하는 데이터베이스와 연동할 때 유리하다.
- SQL을 통해 데이터베이스와 연동한다고 해서 보통 **SQL 매퍼(mapper)** 라 부른다.

## 훈련 목표
- **Mybatis** SQL 매퍼 파일에서 동적 SQL 다루는 방법을 배운다.

## 훈련 내용
- 마이바티스 설정 파일에서 클래스에 대해 별명을 설정한다.
- SQL 매퍼 파일에서 클래스 이름 대신 별명을 사용한다.
- 게시글 검색 기능을 추가한다.
- `<if>` 태그를 사용하여 조건에 따라 SQL 코드를 삽입한다.
- `<where>`, `<choose>`, `<set>`, `<foreach>`, `<sql>` 태그를 적용한다.

## 실습

### 1단계 - fully-qualified class name 에 대해 별명을 부여하기

- src/main/resources/com/eomcs/pms/conf/mybatis-config.xml 변경
  - 클래스 이름에 대해 별명을 지정한다.
```
<typeAliases>
  <typeAlias type="com.eomcs.pms.domain.Board" alias="board"/>
  <typeAlias type="com.eomcs.pms.domain.Member" alias="member"/>
  <typeAlias type="com.eomcs.pms.domain.Project" alias="project"/>
  <typeAlias type="com.eomcs.pms.domain.Task" alias="task"/>
</typeAliases>
```
- src/main/resources/com/eomcs/pms/mapper/XxxMapper.xml 변경
  - 클래스 이름 대신 별명을 사용한다.

일부 자바 클래스에서 대해서 미리 별명이 부여되었다.
- 예)
  - int -> _int
  - java.lang.Integer -> int
  - java.lang.String -> string
  - java.util.Map -> map
  - java.util.HashMap -> hashmap

### 2단계 - 특정 패키지에 소속된 전체 클래스에 대해 별명 부여한다.

- src/main/resources/com/eomcs/pms/conf/mybatis-config.xml 변경
```
<typeAliases>
  <package name="com.eomcs.pms.domain"/>
</typeAliases>
```

### 3단계 - 게시글 검색 기능을 추가한다.

마이바티스의 `if` 태그를 사용하여 동적 SQL을 작성한다.

- 검색어에 해당하는 게시글이 있을 경우,
```
명령> /board/search
검색어? ok
번호, 제목, 작성자, 등록일, 조회수
13, okok4, ccc, 2020-11-09, 0
12, okok2, aaa, 2020-11-09, 0
8, okok, ggg, 2020-11-05, 0
```

- 검색어에 해당하는 게시글이 없을 경우,
```
명령> /board/search
검색어? ㅋㅋ
```

- 검색어를 입력하지 않을 경우
```
명령> /board/search
검색어?
번호, 제목, 작성자, 등록일, 조회수
13, okok4, ccc, 2020-11-09, 0
12, okok2, aaa, 2020-11-09, 0
10, test, ggg, 2020-11-06, 0
9, hul..., aaa, 2020-11-05, 0
8, okok, ggg, 2020-11-05, 0
```

- src/main/resources/com/eomcs/pms/mapper/BoardMapper.xml 변경
  - `findByKeyword` SQL 문을 변경한다.
- com.eomcs.pms.dao.BoardDao 인터페이스 변경
  - `findAll()` 을 제거한다.
- com.eomcs.pms.dao.mariadb.BoardDaoImpl 클래스 변경
  - `findAll()` 를 제거한다.
- com.eomcs.pms.handler.BoardListHandler 클래스 변경
  - `findAll()` 대신 `findByKeyword`를 사용한다.

### 4단계 - 프로젝트 검색 기능을 추가한다.

마이바티스의 `if` 태그를 여러 개 사용하여 동적 SQL을 작성한다.

```
명령> /project/search
[프로젝트 검색]
항목(1:프로젝트명, 2:관리자명, 3:팀원, 그 외: 전체)? 1
검색어? java
번호, 프로젝트명, 시작일 ~ 종료일, 관리자, 팀원
21, javajavaxx, 2020-02-02 ~ 2020-03-03, aaa, [ccc,ddd]
17, java1, 2020-01-01 ~ 2020-02-02, aaa, []
```

- com.eomcs.pms.handler.ProjectSearchHandler 클래스 생성
  - `ProjectListHandler`를 복사해 온다.
  - 사용자에게서 검색 항목과 검색어를 입력 받는 코드를 추가한다.
  - `ProjectDao.findByKeyword()` 를 사용하여 검색 기능을 처리한다.
- com.eomcs.pms.dao.ProjectDao 인터페이스 변경
  - `findAll()` 을 `findByKeyword(String item, String keyword)` 을 변경한다.
- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 클래스 변경
  - `findAll()` 메서드를 `findByKeyword(String item, String keyword)` 를 구현 메소드로 변경한다.
- src/main/resources/com/eomcs/pms/mapper/ProjectMapper.xml 변경
  - `findAll` SQL 문을 `findByKeyword` SQL 문으로 변경한다.
  - if 태그를 사용하여 검색 조건에 따라 where 절을 바꾼다.

```
<select id="findByKeyword" resultMap="ProjectMap" parameterType="map">
...
  <if test="item == 1">
  where p.title like concat('%', #{keyword}, '%')
  </if>
  <if test="item == 2">
  where m.name like concat('%', #{keyword}, '%')
  </if>
  <if test="item == 3">
  where m2.name like concat('%', #{keyword}, '%')
  </if>
</select>
```

- com.eomcs.pms.handler.ProjectListHandler 클래스 변경
  - `findAll()`을 `findByKeyword()` 로 변경한다.
- com.eomcs.pms.handler.TaskAddHandler 클래스 변경
  - `ProjectDao.findAll()`을 `ProjectDao.findByKeyword()` 로 변경한다.
- com.eomcs.pms.handler.TaskUpdateHandler 클래스 변경
  - `ProjectDao.findAll()`을 `ProjectDao.findByKeyword()` 로 변경한다.
- com.eomcs.pms.ClientApp 클래스 변경
  - `/project/search` 명령 처리 코드 추가

### 5단계 - 프로젝트 상세 검색 기능을 추가한다.

마이바티스의 `where` 태그와 `if` 태그를 사용하여 동적 SQL을 작성한다.

```
명령> /project/detailSearch
[프로젝트 상세 검색]   <== 모든 조건을 만족해야 한다.(and)
프로젝트명?(조건에서 제외: 빈 문자열) java   <== 프로젝트 명에 해당 문자열을 포함하는 경우(like)
관리자명?(조건에서 제외: 빈 문자열) aaa   <== 관리자 이름과 일치하는 경우(=)
팀원?(조건에서 제외: 빈 문자열) bbb   <== 팀원 이름과 일치하는 경우(=)
번호, 프로젝트명, 시작일 ~ 종료일, 관리자, 팀원
21, javajavaxx, 2020-02-02 ~ 2020-03-03, aaa, [ccc,ddd]
17, java1, 2020-01-01 ~ 2020-02-02, aaa, []
```

- com.eomcs.pms.handler.ProjectDetailSearchHandler 클래스 생성
  - `ProjectSearchHandler`를 복사해 온다.
  - 사용자에게서 검색 항목과 검색어를 입력 받는 코드를 추가한다.
  - `ProjectDao.findByKeywords()` 를 메서드를 호출한다.
- com.eomcs.pms.dao.ProjectDao 인터페이스 변경
  - `findByKeywords(String title, String owner, String member)` 를 추가한다.
- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 클래스 변경
  - `findByKeywords(String title, String owner, String member)` 메서드를 구현한다.
- src/main/resources/com/eomcs/pms/mapper/ProjectMapper.xml 변경
  - `findByKeywords` SQL 문을 추가한다.
  - if 태그를 사용하여 여러 검색 조건을 만족하는 결과를 얻도록 where 절을 만든다.
- src/main/resources/com/eomcs/pms/mapper/ProjectMapper.xml 변경
  - `findByDetailKeyword` SQL 문을 추가한다.
- com.eomcs.pms.ClientApp 클래스 변경
  - `/project/detailSearch` 명령 처리 코드 추가

### 6단계 - 프로젝트 검색 기능의 mybatis 코드를 변경한다.

- 조건에 상호 배타적인 상황에서는 `if` 태그 보다는 `choose` 태그를 사용하는게 낫다.
- `if` 태그 대신에 `choose` + `where` 태그를 사용해보자.
- src/main/resources/com/eomcs/pms/mapper/ProjectMapper.xml 변경

```
<select id="findByKeyword" resultMap="ProjectMap" parameterType="map">
...
  <where>
    <choose>
      <when test="item == 1">
      p.title like concat('%', #{keyword}, '%')
      </when>
      <when test="item == 2">
      m.name like concat('%', #{keyword}, '%')
      </when>
      <when test="item == 3">
      m2.name like concat('%', #{keyword}, '%')
      </when>
    </choose>
  </where>
...
</select>
```

### 7단계 - 회원 정보 변경할 때 사용자가 입력한 항목만 변경한다.

마이바티스의 `set` 태그와 `if` 태그를 사용하면,
`update` SQL 문을 좀 더 유연하게 만들 수 있다.

- 이전 방식은 한 개의 값을 바꾸더라도 모든 항목의 값을 다시 입력해야 했다.
- 만약 바꾸고 싶은 값만 입력한다면 나머지 값은 빈 문자열이 되었다.
```
명령> /member/update
[회원 변경]
번호? 14
이름(12)?    <--- 입력 안함
이메일(12)? 13@test.com
암호?     <--- 입력 안함
사진(12)?     <--- 입력 안함
전화(12)? 3333
정말 변경하시겠습니까?(y/N) y
회원을 변경하였습니다.

명령> /member/detail
[회원 상세보기]
번호? 14
이름:     <--- 입력 안한 값은 빈 문자열이 들어간다.
이메일: 13@test.com
사진:     <--- 입력 안한 값은 빈 문자열이 들어간다.
전화: 3333
등록일: 2020-11-06

```

- 새 방식은 변경할 값만 입력한다.
- 그리고 입력한 값만 변경된다.

```
명령> /member/update
[회원 변경]
번호? 12
이름(x4)?     <--- 입력 안한 값은 기존 값을 그대로 둔다.
이메일(x4@test.com)? hhh@test.com
암호?    <--- 입력 안한 값은 기존 값을 그대로 둔다.
사진(x4.gif)?     <--- 입력 안한 값은 기존 값을 그대로 둔다.
전화(1111)? 1255
정말 변경하시겠습니까?(y/N) y
회원을 변경하였습니다.

명령> /member/detail
[회원 상세보기]
번호? 12
이름: x4    <--- 입력 안한 값은 그대로다.
이메일: hhh@test.com
사진: x4.gif   <--- 입력 안한 값은 그대로다.
전화: 1255
등록일: 2020-11-05
```

- src/main/resources/com/eomcs/pms/mapper/MemberMapper.xml 변경
  - `update` sql 문의 컬럼 변경 코드를 동적으로 생성하게 한다.


### 8단계 - 프로젝트 멤버를 등록할 때 `insert` 를 한 번만 수행한다.

- 기존 방식은 멤버 수 만큼 `insert` 를 실행하였다.
- 새 방식은 마이바티스의 `foreach` 태그를 이용하여 한 번만 `insert` 하도록 변경한다.

```
이전 방식:
<insert id="insertMember" parameterType="map">
  insert into pms_member_project(member_no, project_no)
  values(#{memberNo},#{projectNo})
</insert>

새 방식: 다음 SQL 추가!
<insert id="insertMembers" parameterType="map">
  insert into pms_member_project(member_no, project_no)
  values
  <foreach collection="members" item="m" separator=",">
    (#{m.no},#{projectNo})
  </foreach>
</insert>
```

- src/main/resources/com/eomcs/pms/mapper/ProjectMapper.xml 변경
  - `insertMembers` SQL 문을 추가한다.
- com.eomcs.pms.dao.ProjectDao 인터페이스 변경
  - `insertMembers(int projectNo, List<Member> members)` 메서드를 추가한다.
- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 클래스 변경
  - `insertMembers(int projectNo, List<Member> members)` 메서드를 구현한다.
  - `insert()`, `update()` 메서드를 변경한다.

### 9단계 - 프로젝트 멤버를 검색할 때 사용하는 SQL 문에서 중복 코드를 분리한다.

- 마이바티스의 `<sql>`와 `<include>` 태그를 활용하면 SQL 문의 일부 조각을 별도로 분리할 수 있다.
- 즉 여러 SQL 문에 중복되는 SQL 코드가 있다면 `<sql>` 태그로 분리하라.
- `<sql>` 태그의 코드를 가져올 때는 `<include>` 태그를 사용하라.

```
<sql id="sql1">
  select
    p.no,
    p.title,
    p.sdt,
    p.edt,
    m.no owner_no,
    m.name owner_name,
    mp.member_no,
    m2.name member_name
  from
    pms_project p
    inner join pms_member m on p.owner=m.no
    left outer join pms_member_project mp on p.no=mp.project_no
    left outer join pms_member m2 on mp.member_no=m2.no
</sql>

<select id="findByKeyword" resultMap="ProjectMap">
  <!-- 별도로 분리된 SQL 코드를 가져오고 싶다면, 다음과 같이 하라. -->
  <include refid="sql1"/>
  order by p.no desc
</select>
```

- src/main/resources/com/eomcs/pms/mapper/ProjectMapper.xml 변경


## 실습 결과
- src/main/resources/com/eomcs/pms/conf/mybatis-config.xml 변경
- src/main/resources/com/eomcs/pms/mapper/BoardMapper.xml 변경
- src/main/resources/com/eomcs/pms/mapper/MemberMapper.xml 변경
- src/main/resources/com/eomcs/pms/mapper/ProjectMapper.xml 변경
- src/main/resources/com/eomcs/pms/mapper/TaskMapper.xml 변경
- src/main/java/com/eomcs/pms/dao/BoardDao.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/BoardDaoImpl.java 변경
- src/main/java/com/eomcs/pms/handler/BoardSearchHandler.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/MemberDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/ProjectDao.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/ProjectDaoImpl.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectSearchHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectDetailSearchHandler.java 변경
- src/main/java/com/eomcs/pms/ClientApp.java 변경
