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
<title>프로젝트</title>
</head>
<body>
<h1>프로젝트(JSP)</h1>
<p><a href='add1'>새 프로젝트</a></p>
<table border='1'>
<thead>
<tr>
<th>번호</th> <th>프로젝트명</th> <th>기간</th> <th>팀장</th> <th>팀원</th>
</tr>
</thead>
<tbody>
<%
List<Project> projects = (List<Project>) request.getAttribute("projects");
for (Project p : projects) {
  StringBuilder strBuilder = new StringBuilder();
  List<Member> members = p.getMembers();
  for (Member m : members) {
    if (strBuilder.length() > 0) {
      strBuilder.append(",");
    }
    strBuilder.append(m.getName());
  }
%>
<tr> 
  <td><%=p.getNo()%></td> 
  <td><a href='detail?no=<%=p.getNo()%>'><%=p.getTitle()%></a></td> 
  <td><%=p.getStartDate()%> ~ <%=p.getEndDate()%></td> 
  <td><%=p.getOwner().getName()%></td> 
  <td><%=strBuilder.toString()%></td> 
</tr>
<%
}
%>
</tbody>
</table>
<%
String item = request.getParameter("item");
if (item == null) {
  item = "";
}

String keyword = request.getParameter("keyword");
if (keyword == null) {
  keyword = "";
}
%>
<form method='get'>
<select name='item'>
  <option value='0' <%=item.equals("0") ? "selected" : ""%>>전체</option>
  <option value='1' <%=item.equals("1") ? "selected" : ""%>>프로젝트명</option>
  <option value='2' <%=item.equals("2") ? "selected" : ""%>>관리자</option>
  <option value='3' <%=item.equals("3") ? "selected" : ""%>>팀원</option>
</select>
<input type='search' name='keyword' value='<%=keyword%>'> 
<button>검색</button>
</form>

<%
String title = request.getParameter("title");
if (title == null) {
  title = "";
}

String owner = request.getParameter("owner");
if (owner == null) {
  owner = "";
}

String member = request.getParameter("member");
if (member == null) {
  member = "";
}
%>
<form method='get'>
<fieldset>
  <legend>상세 검색</legend>
  <table border='1'>
  <tbody>
  <tr> <th>프로젝트명</th> <td><input type='search' name='title' value='<%=title%>'></td> </tr>
  <tr> <th>관리자</th> <td><input type='search' name='owner' value='<%=owner%>'></td> </tr>
  <tr> <th>팀원</th> <td><input type='search' name='member' value='<%=member%>'></td> </tr>
  <tr> <td colspan='2'><button>검색</button></td> </tr>
  </tbody>
  </table>
</fieldset>
</form>
</body>
</html>
  