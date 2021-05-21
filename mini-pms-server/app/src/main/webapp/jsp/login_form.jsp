<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
  trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<title>로그인</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
<link href="css/common.css" rel="stylesheet" >
<style>
  #login-form {
    width: 400px;
    margin: 50px auto;
  }
</style>
</head>
<body>
<div class="container">
<div id="login-form">
<h1>로그인(Bootstrap)</h1>
<form method='post'>
  <div class="mb-3 row">
    <label for="email" class="col-sm-3 col-form-label">이메일</label>
    <div class="col-sm-9">
      <input type="email" class="form-control form-control-sm" id="email" name="email" value='${cookie.email.value}'>
    </div>
  </div>
  <div class="mb-3 row">
    <label for="password" class="col-sm-3 col-form-label">암호</label>
    <div class="col-sm-9">
      <input type="password" class="form-control form-control-sm" id="password" name="password">
    </div>
  </div>
  <div class="mb-3 form-check">
    <input type="checkbox" class="form-check-input" id="saveEmail" name="saveEmail">
    <label class="form-check-label" for="saveEmail">이메일 저장</label>
  </div>
<button class="btn btn-primary btn-sm">로그인</button>
</form>
</div>
</div>
</body>
</html>
