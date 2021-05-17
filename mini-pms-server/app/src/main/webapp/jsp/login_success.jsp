<%@page import="com.eomcs.pms.domain.Member"%>
<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
  trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<title>로그인</title>
</head>
<body>
<h1>로그인 결과(JSP + JSP 액션태그)</h1>
<jsp:useBean id="loginUser" type="com.eomcs.pms.domain.Member" scope="session"/>
<p><%=loginUser.getName()%> 님 환영합니다.</p>
</body>
</html>