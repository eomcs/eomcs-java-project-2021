<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
  trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>사용자 정보</title>
</head>
<body>
<h1>사용자 정보(JSP + JSP 액션태그 + EL + JSTL)</h1>

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
	<table border='1'>
	<tbody>
	<tr>
	  <th>사용자 번호</th> 
	  <td>${loginUser.no}</td></tr>
	<tr>
	  <th>이름</th> 
	  <td>${loginUser.name}</td></tr>
	<tr>
	  <th>이메일</th> 
	  <td>${loginUser.email}</td></tr>
	<tr>
	  <th>사진</th> 
	  <td><a href='${photoUrl}'>
	  <img src='${photo80x80Url}'></a></td></tr>
	</tbody>
	</table>
</c:if>
</body>
</html>
