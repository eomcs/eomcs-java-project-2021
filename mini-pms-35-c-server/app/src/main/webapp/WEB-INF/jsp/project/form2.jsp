<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>새 프로젝트</title>
</head>
<body>
<h1>새 프로젝트: 2단계(JSP)</h1>
<form action='form3' method='post'>
내용: <textarea name='content' rows='10' cols='60'></textarea><br>
시작일: <input type='date' name='startDate'><br>
종료일: <input type='date' name='endDate'><br>
<p><input type='submit' value='다음'>
<a href='list'>취소</a></p>
</form>
</body>
</html>
