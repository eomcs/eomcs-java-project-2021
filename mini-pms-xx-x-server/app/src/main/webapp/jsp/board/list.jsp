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
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
<link href="../css/common.css" rel="stylesheet">
</head>
<body>
<div class="container">
<h1>게시글 목록(Bootstrap)</h1>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a href='add' class="btn btn-primary btn-sm">새 글</a>
    <form class="d-flex">
      <input class="form-control me-2" type="search" name="keyword" value='${param.keyword}' placeholder="검색" aria-label="검색">
      <button class="btn btn-outline-success col-sm-3" type="submit">검색</button>
    </form>
  </div>
</nav>

<table  class="table table-hover">
<thead>
<tr>
<th>번호</th> <th>제목</th> <th>작성자</th> <th>등록일</th> <th>조회수</th>
</tr>
</thead>
<tbody>

<c:forEach items="${list}" var="b">
<tr class="e-title" data-no="${b.no}"> 
  <td>${b.no}</td> 
  <td><a href="detail?no=${b.no}">${b.title}</a></td>
  <td>${b.writer.name}</td>
  <td>${b.registeredDate}</td>
  <td>${b.viewCount}</td>
</tr>
</c:forEach>

</tbody>
</table>

</div>
<script>
var trList = document.querySelectorAll(".e-title");
for (var tr of trList) {
	tr.querySelector("a").onclick = (e) => {
		e.preventDefault();
	};

	tr.onclick = (e) => {
		var boardNo = e.currentTarget.getAttribute("data-no");
		location.href = "detail?no=" + boardNo;
	};

}

</script>
</body>
</html>
