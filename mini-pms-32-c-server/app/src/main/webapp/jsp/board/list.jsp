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
<h1>게시글 목록(JSP + JSP 액션태그 + EL)</h1>
<p><a href='add'>새 글</a></p>
<table border='1'>
<thead>
<tr>
<th>번호</th> <th>제목</th> <th>작성자</th> <th>등록일</th> <th>조회수</th>
</tr>
</thead>
<tbody>
<jsp:useBean id="list" type="List<Board>" scope="request"/>
<% 
for (Board b : list) {
  pageContext.setAttribute("b", b);
%>
<tr> 
  <td>${b.no}</td> 
  <td><a href='detail?no=${b.no}'>${b.title}</a></td>
  <td>${b.writer.name}</td>
  <td>${b.registeredDate}</td>
  <td>${b.viewCount}</td>
</tr>
<%
}
%>
</tbody>
</table>

<form action='list' method='get'>
<input type='search' name='keyword' value='${param.keyword}'> 
<button>검색</button>
</form>

</body>
</html>
