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
<h1>회원(JSP + JSP 액션태그 + EL)</h1>
<p><a href='add'>새 회원</a></p>
<table border='1'>
<thead>
<tr>
<th>번호</th> <th> </th><th>이름</th> <th>이메일</th> <th>전화</th>
</tr>
</thead>
<tbody>
<jsp:useBean id="list" type="List<Member>" scope="request"/>
<%
for (Member m : list) {
  pageContext.setAttribute("m", m);
  pageContext.setAttribute("photoUrl", 
      m.getPhoto() != null ? "../upload/" + m.getPhoto() + "_30x30.jpg" : "../images/person_30x30.jpg");
%>
<tr> 
  <td>${m.no}</td> 
  <td><img src='${photoUrl}'></td> 
  <td><a href='detail?no=${m.no}'>${m.name}</a></td> 
  <td>${m.email}</td> 
  <td>${m.tel}</td> </tr>
<%}%>
</tbody>
</table>

<form action='list' method='get'>
<input type='search' name='keyword' value='${param.keyword}'> 
<button>검색</button>
</form>
</body>
</html>

