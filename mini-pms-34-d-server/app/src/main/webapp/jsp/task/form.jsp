<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>새 작업</title>
</head>
<body>
<h1>새 작업(JSP + JSP 액션태그 + EL + JSTL)</h1>
<form action='add' method='post'>
프로젝트: <select name='projectNo'>

<c:forEach items="${projects}" var="p">
  <option value='${p.no}'>${p.title}</option>
</c:forEach>

</select><br>
작업: <input type='text' name='content'><br>
마감일: <input type='date' name='deadline'><br>
담당자: <select name='owner.no'>

<c:forEach items="${members}" var="m">
  <option value='${m.no}'>${m.name}</option>
</c:forEach>
</select><br>
상태: 
<input type='radio' name='status' value='0' checked>신규 
<input type='radio' name='status' value='1'>진행중 
<input type='radio' name='status' value='2'>완료 
<input type='submit' value='등록'>
</form>
</body>
</html>
    