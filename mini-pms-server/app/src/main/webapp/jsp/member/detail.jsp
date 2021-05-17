<%@page import="com.eomcs.pms.domain.Member"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.eomcs.pms.domain.Board"%>
<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<title>회원</title>
</head>
<body>
<h1>회원 정보(JSP + JSP 액션태그)</h1>
<jsp:useBean id="member" type="com.eomcs.pms.domain.Member" scope="request"/>
<% 
if (member != null) {
%>
<form action='update' method='post' enctype='multipart/form-data'>
<table border='1'>
<tbody>
<tr>
  <th>번호</th> 
  <td><input type='text' name='no' value='<%=member.getNo()%>' readonly></td></tr>
<tr>
  <th>이름</th> 
  <td><input name='name' type='text' value='<%=member.getName()%>'></td></tr>
<tr>
  <th>이메일</th> 
  <td><input name='email' type='email' value='<%=member.getEmail()%>'></td></tr>
<tr>
  <th>암호</th> 
  <td><input name='password' type='password'></td></tr>
<tr>
  <th>전화</th> 
  <td><input name='tel' type='tel' value='<%=member.getTel() != null ? member.getTel() : ""%>'></td></tr>
<tr>
  <th>가입일</th> 
  <td><%=member.getRegisteredDate()%></td></tr>
<tr>
  <th>사진</th> 
  <td><a href='<%=member.getPhoto() != null ? "../upload/" + member.getPhoto() : ""%>'>
  <img src='<%=member.getPhoto() != null ? 
      "../upload/" + member.getPhoto() + "_80x80.jpg" : "../images/person_80x80.jpg"%>'></a><br>
  <input name='photo' type='file'></td></tr>
</tbody>
<tfoot>
<tr><td colspan='2'>
<input type='submit' value='변경'> <a href='delete?no=<%=member.getNo()%>'>삭제</a> 
</td></tr>
</tfoot>
</table>
</form>

<%} else {%>
<p>해당 번호의 회원이 없습니다.</p>
<%}%>

<p><a href='list'>목록</a></p>
</body>
</html>
</html>