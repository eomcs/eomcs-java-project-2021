DROP TABLE IF EXISTS pms_member RESTRICT;
DROP TABLE IF EXISTS pms_board RESTRICT;
DROP TABLE IF EXISTS pms_project RESTRICT;
DROP TABLE IF EXISTS pms_task RESTRICT;
DROP TABLE IF EXISTS pms_member_project RESTRICT;

create table pms_member(
  no int not null,
  name varchar(30) not null,
  email varchar(50) not null,
  password varchar(50) not null,
  photo varchar(255),
  tel varchar(20),
  cdt datetime default now()
);

alter table pms_member
  add constraint pms_member_pk primary key(no);

alter table pms_member
  modify column no int not null auto_increment;

alter table pms_member
  add constraint pms_member_uk unique (no);

create table pms_board(
  no int not null,
  title varchar(255) not null,
  content text not null,
  writer int not null,
  cdt datetime default now(),
  like_cnt int default 0,
  vw_cnt int default 0
);

alter table pms_board
  add constraint pms_board_pk primary key(no);

alter table pms_board
  modify column no int not null auto_increment;

alter table pms_board
  add constraint pms_board_fk foreign key(writer) references pms_member(no);
  

create table pms_project(
  no int not null,
  title varchar(255) not null,
  content text not null,
  sdt date not null,
  edt date not null,
  owner int not null, 
  state int default 1
);

alter table pms_project
  add constraint pms_project_pk primary key(no);

alter table pms_project
  modify column no int not null auto_increment;

alter table pms_project
  add constraint pms_project_fk foreign key(owner) references pms_member(no);


create table pms_task(
  no int not null,
  content text not null,
  deadline date not null,
  owner int not null,   /* pms_member 의 PK 컬럼을 가리키는 외부키다*/
  project_no int not null, /* pms_project 의 PK 컬럼을 가리키는 외부키다*/
  status int default 0
);

alter table pms_task
  add constraint pms_task_pk primary key(no);

alter table pms_task
  modify column no int not null auto_increment;

alter table pms_task
  add constraint pms_task_fk1 foreign key(owner, project_no)
      references pms_member_project(member_no, project_no);


/* 프로젝트와 멤버의 다대다 관계를 저장할 테이블을 정의한다.*/
create table pms_member_project(
  member_no int not null,
  project_no int not null
);

/* 다대다 관계를 저장할 컬럼의 대해 FK를 설정한다. */
alter table pms_member_project
  add constraint pms_member_project_fk1 foreign key(member_no) references pms_member(no),
  add constraint pms_member_project_fk2 foreign key(project_no) references pms_project(no);

/* 프로젝트-멤버 정보가 중복 저장되지 않도록 PK로 설정한다 */
alter table pms_member_project
  add constraint pms_member_project_pk primary key(member_no, project_no);
