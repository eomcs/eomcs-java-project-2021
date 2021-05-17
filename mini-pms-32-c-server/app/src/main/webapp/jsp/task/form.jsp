<%@page import="com.eomcs.pms.domain.Member"%>
<%@page import="com.eomcs.pms.domain.Project"%>
<%@page import="java.util.List"%>
<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>새 작업</title>
</head>
<body>
<h1>새 작업(JSP + JSP 액션태그)</h1>
<form action='add' method='post'>
프로젝트: <select name='projectNo'>
<jsp:useBean id="projects" type="List<Project>" scope="request"/>
<%
for (Project p : projects) {
%>
  <option value='<%=p.getNo()%>'><%=p.getTitle()%></option>
<%
}
%>
</select><br>
작업: <input type='text' name='content'><br>
마감일: <input type='date' name='deadline'><br>
담당자: <select name='owner'>
<jsp:useBean id="members" type="List<Member>" scope="request"/>
<%
for (Member m : members) {
%>
  <option value='<%=m.getNo()%>'><%=m.getName()%></option>
<%
}
%>
</select><br>
상태: 
<input type='radio' name='status' value='0' checked>신규 
<input type='radio' name='status' value='1'>진행중 
<input type='radio' name='status' value='2'>완료 
<input type='submit' value='등록'>
</form>
</body>
</html>
    