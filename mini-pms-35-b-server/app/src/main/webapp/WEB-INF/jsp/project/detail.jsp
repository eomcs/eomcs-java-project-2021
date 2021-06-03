<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>프로젝트 상세</title>
</head>
<body>
<h1>프로젝트 상세보기(JSP + JSP 액션태그 + 선언 엘리먼트 + EL)</h1>
<form action='update' method='post'>
번호: <input type='text' name='no' value='${project.no}' readonly><br>
제목: <input type='text' name='title' value='${project.title}'><br>
내용: <textarea name='content' rows='10' cols='60'>${project.content}</textarea><br>
시작일: <input type='date' name='startDate' value='${project.startDate}'><br>
종료일: <input type='date' name='endDate' value='${project.endDate}'><br>
관리자: ${project.owner.name}<br>
팀원: <br>
<jsp:include page="member_list.jsp"/>
<input type='submit' value='변경'> 
<a href='delete?no=${project.no}'>삭제</a>
</form>
<p><a href='list'>목록</a></p>
</body>
</html>
  