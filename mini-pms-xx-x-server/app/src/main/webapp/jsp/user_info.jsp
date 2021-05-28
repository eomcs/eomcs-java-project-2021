<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
  trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>사용자 정보</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
<link href="css/common.css" rel="stylesheet" >
</head>
<body>
<div class="container">

<h1>사용자 정보</h1>
<c:if test="${empty loginUser}">
  <p>로그인 하지 않았습니다.</p>
</c:if>

<c:if test="${not empty loginUser}">
  <c:if test="${not empty loginUser.photo}">
    <c:set var="photo80x80Url">upload/${loginUser.photo}_80x80.jpg</c:set>
    <c:set var="photoUrl">upload/${loginUser.photo}</c:set>
  </c:if>
  <c:if test="${empty loginUser.photo}">
    <c:set var="photo80x80Url">images/person_80x80.jpg</c:set>
    <c:set var="photoUrl"></c:set>
  </c:if>
  
  <div class="mb-3 row">
    <label for="no" class="col-sm-2 col-form-label">사용자 번호</label>
    <div class="col-sm-10">
      <input type="text" class="form-control-plaintext form-control-sm" id="no" value='${loginUser.no}'>
    </div>
  </div>
  <div class="mb-3 row">
    <label for="name" class="col-sm-2 col-form-label">이름</label>
    <div class="col-sm-10">
      <input type="text" class="form-control-plaintext form-control-sm" id="name" value='${loginUser.name}'>
    </div>
  </div>
  <div class="mb-3 row">
    <label for="email" class="col-sm-2 col-form-label">이메일</label>
    <div class="col-sm-10">
      <input type="text" class="form-control-plaintext form-control-sm" id="email" value='${loginUser.email}'>
    </div>
  </div>
  <div class="mb-3 row">
    <label for="email" class="col-sm-2 col-form-label">사진</label>
    <div class="col-sm-10">
    <a href='${photoUrl}'>
      <img src="${photo80x80Url}" class="img-thumbnail" alt="사용자 사진">
    </a>
    </div>
  </div>
</c:if>
</div>
</body>
</html>
