<%@page import="com.eomcs.pms.domain.Board"%>
<%@page import="java.util.List"%>
<%@ page 
    language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>게시글 목록</title>
</head>
<body>
<h1>게시글 목록(JSP2)</h1>
<p><a href='form.html'>새 글</a></p>
<table border='1'>
<thead>
<tr>
<th>번호</th> <th>제목</th> <th>작성자</th> <th>등록일</th> <th>조회수</th>
</tr>
</thead>
<tbody>
<%
List<Board> list = (List<Board>) request.getAttribute("list");
for (Board b : list) {
  out.println("<tr>"); 
  out.println("<td>" + b.getNo() + "</td>"); 
  out.println("<td><a href='detail?no=" + b.getNo() + "'>" + b.getTitle() + "</a></td>"); 
  out.println("<td>" + b.getWriter().getName() + "</td>"); 
  out.println("<td>" + b.getRegisteredDate() + "</td>"); 
  out.println("<td>" + b.getViewCount() + "</td>"); 
  out.println("</tr>");  
}
%>
</tbody>
</table>
<form action='search' method='get'>
<input type='text' name='keyword'> 
<button>검색</button>
</form>
</body>
</html>
