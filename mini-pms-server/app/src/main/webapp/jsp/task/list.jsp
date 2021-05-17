<%@page import="com.eomcs.pms.domain.Task"%>
<%@page import="com.eomcs.pms.domain.Project"%>
<%@page import="java.util.List"%>
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
<h1>작업(JSP)</h1>
<p><a href='add'>새 작업</a></p>
<%
int projectNo = (int) request.getAttribute("projectNo");
List<Project> projects = (List<Project>) request.getAttribute("projects");
%>
<form>
프로젝트: <select name='projectNo'>
  <option value='0' selected>전체</option>
<% 
for (Project p : projects) {
%>
  <option value='<%=p.getNo()%>'  <%=p.getNo() == projectNo ? "selected" : ""%>><%=p.getTitle()%></option>
<%
}
%>
</select>
<button>검색</button>
</form>
<table border='1'>
<thead>
<tr>
<th>번호</th> <th>작업</th> <th>마감일</th> <th>담당자</th> <th>상태</th>
</tr>
</thead>
<tbody>
<%
List<Task> tasks = (List<Task>) request.getAttribute("tasks");
if (tasks.size() == 0) {
%>
<tr>
  <td colspan='5'>해당 번호의 프로젝트가 없거나 또는 등록된 작업이 없습니다.</td>
</tr>
<%
} else {
  for (Task t : tasks) {
    if (projectNo != t.getProjectNo()) {
%>
<tr>
  <td colspan='5'>프로젝트: '<%=t.getProjectTitle()%>'</td>
</tr>
<%  }%>
<tr> 
  <td><%=t.getNo()%></td> 
  <td><a href='detail?no=<%=t.getNo()%>'><%=t.getContent()%></a></td> 
  <td><%=t.getDeadline()%></td> 
  <td><%=t.getOwner().getName()%></td> 
  <td><%=Task.getStatusLabel(t.getStatus())%></td> 
</tr>
<%}
}
%>
</tbody>
</table>
</body>
</html>
 