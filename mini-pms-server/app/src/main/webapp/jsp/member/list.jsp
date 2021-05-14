<%@page import="com.eomcs.pms.domain.Member"%>
<%@page import="java.util.List"%>
<%@ page 
    language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<title>회원</title>
</head>
<body>
<h1>회원(JSP)</h1>
<p><a href='form.html'>새 회원</a></p>
<table border='1'>
<thead>
<tr>
<th>번호</th> <th> </th><th>이름</th> <th>이메일</th> <th>전화</th>
</tr>
</thead>
<tbody>
<%
List<Member> list = (List<Member>) request.getAttribute("list");
for (Member m : list) {
%>
<tr> 
  <td><%=m.getNo()%></td> 
  <td><img src='<%=m.getPhoto() != null ? 
      "../upload/" + m.getPhoto() + "_30x30.jpg" : "../images/person_30x30.jpg"%>'></td> 
  <td><a href='detail?no=<%=m.getNo()%>'><%=m.getName()%></a></td> 
  <td><%=m.getEmail()%></td> 
  <td><%=m.getTel()%></td> </tr>
<%}%>
</tbody>
</table>

<% String keyword = request.getParameter("keyword");%>
<form action='list' method='get'>
<input type='search' name='keyword' value='<%=keyword != null ? keyword : ""%>'> 
<button>검색</button>
</form>
</body>
</html>

