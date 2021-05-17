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
<title>게시글 상세</title>
</head>
<body>
<h1>게시글 상세보기(JSP + JSP 액션태그 + EL)</h1>
<% 
if (request.getAttribute("board") != null) {
%>
<form action='update' method='post'>
<table border='1'>
<tbody>
<tr><th>번호</th> <td><input type='text' name='no' value='${board.no}' readonly></td></tr>
<tr><th>제목</th> <td><input name='title' type='text' value='${board.title}'></td></tr>
<tr><th>내용</th> <td><textarea name='content' rows='10' cols='60'>${board.content}</textarea></td></tr>
<tr><th>작성자</th> <td>${board.writer.name}</td></tr>
<tr><th>등록일</th> <td>${board.registeredDate2}</td></tr>
<tr><th>조회수</th> <td>${board.viewCount}</td></tr>
<tr><th>좋아요</th> <td>${board.like}</td></tr>
</tbody>
<jsp:useBean id="loginUser" type="com.eomcs.pms.domain.Member" scope="session"/>
<% 
Board board = (Board) request.getAttribute("board");
if (loginUser != null && board.getWriter().getNo() == loginUser.getNo()) {
%>
<tfoot>
<tr>
  <td colspan='2'>
    <input type='submit' value='변경'><a href='delete?no=${board.no}'>삭제</a>
  </td>
</tr>
</tfoot>
<%}%>
      
</table>
</form>

<%} else {%>
<p>해당 번호의 게시글이 없습니다.</p>
<%}%>

<p><a href='list'>목록</a></p>
</body>
</html>