<%@page import="com.eomcs.pms.domain.Member"%>
<%@page import="java.util.List"%>
<%@page import="com.eomcs.pms.domain.Task"%>
<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
  trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<title>작업</title>
</head>
<body>
<h1>작업 정보(JSP + JSP 액션태그)</h1>
<jsp:useBean id="task" type="com.eomcs.pms.domain.Task" scope="request"/>
<form action='update' method='post'>
<input type='hidden' name='projectNo' value='<%=task.getProjectNo()%>'>
<table border='1'>
<tbody>
<tr>
  <th>프로젝트</th> 
  <td><%=task.getProjectTitle()%></td></tr>
<tr>
  <th>번호</th> 
  <td><input name='no' type='text' value='<%=task.getNo()%>' readonly></td></tr>
<tr>
  <th>작업</th> 
  <td><input name='content' type='text' value='<%=task.getContent()%>'></td></tr>
<tr>
  <th>마감일</th> 
  <td><input name='deadline' type='date' value='<%=task.getDeadline()%>'></td></tr>
<tr>
  <th>상태</th> 
  <td>
    <input type='radio' name='status' value='0' <%=task.getStatus() == 0 ? "checked" : ""%>>신규 
    <input type='radio' name='status' value='1' <%=task.getStatus() == 1 ? "checked" : ""%>>진행중 
    <input type='radio' name='status' value='2' <%=task.getStatus() == 2 ? "checked" : ""%>>완료 </td></tr>
<tr>
  <th>담당자</th> 
  <td><select name='owner'>
<jsp:useBean id="members" type="List<Member>" scope="request"/>
<%
for (Member m : members) {
%>
  <option value='<%=m.getNo()%>' <%=task.getOwner().getNo() == m.getNo() ? "selected" : ""%>><%=m.getName()%></option>
<%
}
%>
</select><br>
</tbody>
<tfoot>
<tr><td colspan='2'>
<input type='submit' value='변경'> <a href='delete?no=<%=task.getNo()%>'>삭제</a> 
</td></tr>
</tfoot>
</table>
</form>
<p><a href='list'>목록</a></p>
</body>
</html>

 