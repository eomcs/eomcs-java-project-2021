<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
  trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<title>작업</title>
</head>
<body>
<h1>작업 정보(JSP + JSP 액션태그 + EL + JSTL)</h1>
<form action='update' method='post'>
<input type='hidden' name='projectNo' value='${task.projectNo}'>
<table border='1'>
<tbody>
<tr>
  <th>프로젝트</th> 
  <td>${task.projectTitle}</td></tr>
<tr>
  <th>번호</th> 
  <td><input name='no' type='text' value='${task.no}' readonly></td></tr>
<tr>
  <th>작업</th> 
  <td><input name='content' type='text' value='${task.content}'></td></tr>
<tr>
  <th>마감일</th> 
  <td><input name='deadline' type='date' value='${task.deadline}'></td></tr>
<tr>
  <th>상태</th> 
  <td>
    <input type='radio' name='status' value='0' ${task.status == 0 ? "checked" : ""}>신규 
    <input type='radio' name='status' value='1' ${task.status == 1 ? "checked" : ""}>진행중 
    <input type='radio' name='status' value='2' ${task.status == 2 ? "checked" : ""}>완료 </td></tr>
<tr>
  <th>담당자</th> 
  <td><select name='owner'>
  
<c:forEach items="${members}" var="m">
  <option value='${m.no}' ${task.owner.no == m.no ? "selected" : ""}>${m.name}</option>
</c:forEach>

</select><br>
</tbody>
<tfoot>
<tr><td colspan='2'>
<input type='submit' value='변경'> <a href='delete?no=${task.no}'>삭제</a> 
</td></tr>
</tfoot>
</table>
</form>
<p><a href='list'>목록</a></p>
</body>
</html>

 