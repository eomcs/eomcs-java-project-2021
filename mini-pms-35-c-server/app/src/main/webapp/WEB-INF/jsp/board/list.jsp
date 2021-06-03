<%@ page 
    language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>게시글 목록</title>
</head>
<body>
<h1>게시글 목록(JSP + JSP 액션태그 + EL + JSTL)</h1>
<p><a href='form'>새 글</a></p>
<table border='1'>
<thead>
<tr>
<th>번호</th> <th>제목</th> <th>작성자</th> <th>등록일</th> <th>조회수</th>
</tr>
</thead>
<tbody>

<c:forEach items="${list}" var="b">
<tr> 
  <td>${b.no}</td> 
  <td><a href='detail?no=${b.no}'>${b.title}</a></td>
  <td>${b.writer.name}</td>
  <td>${b.registeredDate}</td>
  <td>${b.viewCount}</td>
</tr>
</c:forEach>

</tbody>
</table>

<form action='list' method='get'>
<input type='search' name='keyword' value='${param.keyword}'> 
<button>검색</button>
</form>

<p>
<c:if test="${pageNo > 1}">
  <a href="list?pageNo=${pageNo-1}&pageSize=${pageSize}&keyword=${param.keyword}">[이전]</a>
</c:if>
<c:if test="${pageNo <= 1}">
  [이전]
</c:if>
  ${pageNo}
<c:if test="${pageNo < totalPage}">
  <a href="list?pageNo=${pageNo+1}&pageSize=${pageSize}&keyword=${param.keyword}">[다음]</a>
</c:if>
<c:if test="${pageNo >= totalPage}">
  [다음]
</c:if>
</p>

</body>
</html>
