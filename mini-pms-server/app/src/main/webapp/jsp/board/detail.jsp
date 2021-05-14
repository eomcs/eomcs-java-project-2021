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
<h1>게시글 상세보기(JSP)</h1>
<% 
Board b = (Board) request.getAttribute("board");
if (b != null) {
%>
<form action='update' method='post'>
<table border='1'>
<tbody>
<tr><th>번호</th> <td><input type='text' name='no' value='<%=b.getNo()%>' readonly></td></tr>
<tr><th>제목</th> <td><input name='title' type='text' value='<%=b.getTitle()%>'></td></tr>
<tr><th>내용</th> <td><textarea name='content' rows='10' cols='60'><%=b.getContent()%></textarea></td></tr>
<tr><th>작성자</th> <td><%=b.getWriter().getName()%></td></tr>
<tr><th>등록일</th> <td><%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(b.getRegisteredDate())%></td></tr>
<tr><th>조회수</th> <td><%=b.getViewCount()%></td></tr>
<tr><th>좋아요</th> <td><%=b.getLike()%></td></tr>
</tbody>
<% 
Member loginUser = (Member) session.getAttribute("loginUser");
if (loginUser != null && b.getWriter().getNo() == loginUser.getNo()) {
%>
<tfoot>
<tr>
  <td colspan='2'>
    <input type='submit' value='변경'><a href='delete?no=<%=b.getNo()%>'>삭제</a>
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