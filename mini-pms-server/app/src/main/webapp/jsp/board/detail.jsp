<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>게시글 상세</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
<link href="../css/common.css" rel="stylesheet" >
</head>
<body>
<div class="container">
<h1>게시글 상세보기(Bootstrap)</h1>

<c:if test="${empty board}">
<p>해당 번호의 게시글이 없습니다.</p>
</c:if>

<c:if test="${not empty board}">
<form action='update' method='post'>
  <div class="mb-3 row">
    <label for="no" class="col-sm-1 col-form-label">번호</label>
    <div class="col-sm-7">
      <input type="text" class="form-control-plaintext form-control-sm" id="no" name="no" value='${board.no}'>
    </div>
  </div>
  <div class="mb-3 row">
    <label for="title" class="col-sm-1 col-form-label">제목</label>
    <div class="col-sm-7">
      <input type="text" class="form-control form-control-sm" id="title" name="title" value='${board.title}'>
    </div>
  </div>
  <div class="mb-3 row">
    <label for="content" class="col-sm-1 col-form-label">내용</label>
    <div class="col-sm-7">
      <textarea class="form-control form-control-sm" id="content"name="content" rows="10" cols="60">${board.content}</textarea>
    </div>
  </div>
  <div class="mb-3 row">
    <label for="writer" class="col-sm-1 col-form-label">작성자</label>
    <div class="col-sm-7">
      <input type="text" class="form-control-plaintext form-control-sm" id="writer" value='${board.writer.name}'>
    </div>
  </div>
  <div class="mb-3 row">
    <label for="registeredDate" class="col-sm-1 col-form-label">등록일</label>
    <div class="col-sm-7">
      <input type="text" class="form-control-plaintext form-control-sm" id="registeredDate" value='${board.registeredDate2}'>
    </div>
  </div>
  <div class="mb-3 row">
    <label for="viewCount" class="col-sm-1 col-form-label">조회수</label>
    <div class="col-sm-7">
      <input type="text" class="form-control-plaintext form-control-sm" id="viewCount" value='${board.viewCount}'>
    </div>
  </div>
  <div class="mb-3 row">
    <label for="like" class="col-sm-1 col-form-label">좋아요</label>
    <div class="col-sm-7">
      <input type="text" class="form-control-plaintext form-control-sm" id="registeredDate" value='${board.like}'>
    </div>
  </div>  

  <c:if test="${not empty loginUser and loginUser.no == board.writer.no}">
	    <button class="btn btn-primary btn-sm">변경</button>
	    <a href='delete?no=${board.no}' class="btn btn-primary btn-sm">삭제</a>
  </c:if>
	
	<a href='list' class="btn btn-primary btn-sm">목록</a>
	
</form>
</c:if>


</div>
</body>
</html>