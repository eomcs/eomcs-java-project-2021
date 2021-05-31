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
<h1>로그인(JSP + JSP 액션태그 + EL)</h1>
<form method='post'>
<table border='1'>
<tbody>
<tr><th>이메일</th><td><input name='email' type='email' value='${cookie.email.value}'></td></tr>
<tr><th>암호</th><td><input name='password' type='password'></td></tr>
</tbody>
<tfoot>
<tr><td colspan='2'><input type='checkbox' name='saveEmail'>이메일 저장</td></tr>
<tr><td colspan='2'><button>로그인</button></td></tr>
</tfoot>
</table>
</form>
</body>
</html>
