<%@page import="com.eomcs.pms.domain.Member"%>
<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
  trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<title>사용자 정보</title>
</head>
<body>
<h1>사용자 정보(JSP + JSP 액션태그 + EL)</h1>
<jsp:useBean id="loginUser" type="com.eomcs.pms.domain.Member" scope="session"></jsp:useBean>
<%
if (loginUser == null) {
%>
  <p>로그인 하지 않았습니다.</p>
<%
} else {
  pageContext.setAttribute("photo80x80Url", 
      loginUser.getPhoto() != null ? 
          "upload/" + loginUser.getPhoto() + "_80x80.jpg" : "images/person_80x80.jpg");
  pageContext.setAttribute("photoUrl", 
      loginUser.getPhoto() != null ? 
          "upload/" + loginUser.getPhoto() : "");
%>
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
<%
}
%>
</body>
</html>
