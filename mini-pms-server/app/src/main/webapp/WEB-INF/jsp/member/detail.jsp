<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>회원</title>
</head>
<body>
<h1>회원 정보(JSP + JSP 액션태그 + EL + JSTL)</h1>

<c:if test="${not empty member}">
  <c:if test="${not empty member.photo}">
    <c:set var="photo80x80Url">../../upload/${member.photo}_80x80.jpg</c:set>
    <c:set var="photoUrl">../upload/${member.photo}</c:set>
  </c:if>
  <c:if test="${empty member.photo}">
    <c:set var="photo80x80Url">../../images/person_80x80.jpg</c:set>
    <c:set var="photoUrl"></c:set>
  </c:if>
  
	<form action='update' method='post' enctype='multipart/form-data'>
	<table border='1'>
	<tbody>
	<tr>
	  <th>번호</th> 
	  <td><input type='text' name='no' value='${member.no}' readonly></td></tr>
	<tr>
	  <th>이름</th> 
	  <td><input name='name' type='text' value='${member.name}'></td></tr>
	<tr>
	  <th>이메일</th> 
	  <td><input name='email' type='email' value='${member.email}'></td></tr>
	<tr>
	  <th>암호</th> 
	  <td><input name='password' type='password'></td></tr>
	<tr>
	  <th>전화</th> 
	  <td><input name='tel' type='tel' value='${member.tel}'></td></tr>
	<tr>
	  <th>가입일</th> 
	  <td>${member.registeredDate}</td></tr>
	<tr>
	  <th>사진</th> 
	  <td><a href='${photoUrl}'>
	  <img src='${photo80x80Url}'></a><br>
	  <input name='photoFile' type='file'></td></tr>
	</tbody>
	<tfoot>
	<tr><td colspan='2'>
	<input type='submit' value='변경'> <a href='delete?no=${member.no}'>삭제</a> 
	</td></tr>
	</tfoot>
	</table>
	</form>
</c:if>

<c:if test="${empty member}">
  <p>해당 번호의 회원이 없습니다.</p>
</c:if>

<p><a href='list'>목록</a></p>
</body>
</html>
</html>