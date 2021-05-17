<%@page import="com.eomcs.pms.domain.Project"%>
<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>프로젝트 상세</title>
</head>
<body>
<h1>프로젝트 상세보기(JSP)</h1>
<%
Project project = (Project) request.getAttribute("project");
%>
<form action='update' method='post'>
번호: <input type='text' name='no' value='<%=project.getNo()%>' readonly><br>
제목: <input type='text' name='title' value='<%=project.getTitle()%>'><br>
내용: <textarea name='content' rows='10' cols='60'><%=project.getContent()%></textarea><br>
시작일: <input type='date' name='startDate' value='<%=project.getStartDate()%>'><br>
종료일: <input type='date' name='endDate' value='<%=project.getEndDate()%>'><br>
관리자: <%=project.getOwner().getName()%><br>
팀원: <br>
<%
// JSP에서 자바 코드로 다른 서블릿(JSP)을 인클루드 하는 경우,
// 인클루드 출력이 먼저 수행된다.
// 해결책?
// - 다른 서블릿(JSP)을 인클루드 하기 전에 현재 서블릿(JSP)을 출력을 먼저 수행하라.
out.flush();

request.getRequestDispatcher("/jsp/project/member_list.jsp").include(request,response);
%>
<input type='submit' value='변경'> 
<a href='delete?no=<%=project.getNo()%>'>삭제</a>
</form>
<p><a href='list'>목록</a></p>
</body>
</html>
  