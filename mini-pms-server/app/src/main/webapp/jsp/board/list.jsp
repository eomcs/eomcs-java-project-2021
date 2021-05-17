<%@page import="com.eomcs.pms.domain.Board"%>
<%@page import="java.util.List"%>
<%@ page 
    language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<title>게시글 목록</title>
</head>
<body>
<h1>게시글 목록(JSP)</h1>
<p><a href='add'>새 글</a></p>
<table border='1'>
<thead>
<tr>
<th>번호</th> <th>제목</th> <th>작성자</th> <th>등록일</th> <th>조회수</th>
</tr>
</thead>
<tbody>
<% // scriptlet element = 자바 코드를 두는 블록 
List<Board> list = (List<Board>) request.getAttribute("list");
for (Board b : list) {
%>
<tr> 
  <td><%=b.getNo()%></td> 
  <td><a href='detail?no=<%=b.getNo()%>'><%=b.getTitle()%></a></td>
  <td><%=b.getWriter().getName()%></td>
  <td><%=b.getRegisteredDate()%></td>
  <td><%=b.getViewCount()%></td>
</tr>
<%
}
%>
</tbody>
</table>

<% String keyword = request.getParameter("keyword");%>
<form action='list' method='get'>
<input type='search' name='keyword' value='<%=keyword != null ? keyword : ""%>'> 
<button>검색</button>
</form>
</body>
</html>
