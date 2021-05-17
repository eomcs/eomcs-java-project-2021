<%@page import="com.eomcs.pms.domain.Project"%>
<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>프로젝트 상세</title>
</head>
<body>
<h1>프로젝트 상세보기(JSP + JSP 액션태그)</h1>
<jsp:useBean id="project" type="com.eomcs.pms.domain.Project" scope="request"/>
<form action='update' method='post'>
번호: <input type='text' name='no' value='<%=project.getNo()%>' readonly><br>
제목: <input type='text' name='title' value='<%=project.getTitle()%>'><br>
내용: <textarea name='content' rows='10' cols='60'><%=project.getContent()%></textarea><br>
시작일: <input type='date' name='startDate' value='<%=project.getStartDate()%>'><br>
종료일: <input type='date' name='endDate' value='<%=project.getEndDate()%>'><br>
관리자: <%=project.getOwner().getName()%><br>
팀원: <br>
<jsp:include page="/jsp/project/member_list.jsp"/>
<input type='submit' value='변경'> 
<a href='delete?no=<%=project.getNo()%>'>삭제</a>
</form>
<p><a href='list'>목록</a></p>
</body>
</html>
  