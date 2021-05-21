<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>새 게시글</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
<link href="../css/common.css" rel="stylesheet">
</head>
<body>
<div class="container">
<h1>새 게시글</h1>
<form action="add" method="post">
  <div class="mb-3 row">
    <label for="title" class="col-sm-1 col-form-label">제목</label>
    <div class="col-sm-7">
      <input type="text" class="form-control form-control-sm" id="title" name="title">
    </div>
  </div>
  <div class="mb-3 row">
    <label for="content" class="col-sm-1 col-form-label">내용</label>
    <div class="col-sm-7">
      <textarea class="form-control form-control-sm" id="content"name="content" rows="10" cols="60"></textarea>
    </div>
  </div>
  <button class="btn btn-primary btn-sm">등록</button>
</form>
</div>
</body>
</html>