<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
  trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>프로젝트</title>
</head>
<body>
<h1>프로젝트(JSP + JSP 액션태그 + EL + JSTL)</h1>
<p><a href='form1'>새 프로젝트</a></p>
<table border='1'>
<thead>
<tr>
<th>번호</th> <th>프로젝트명</th> <th>기간</th> <th>팀장</th> <th>팀원</th>
</tr>
</thead>
<tbody>

<c:forEach items="${projects}" var="p">
<tr> 
  <td>${p.no}</td> 
  <td><a href='detail?no=${p.no}'>${p.title}</a></td> 
  <td>${p.startDate} ~ ${p.endDate}</td> 
  <td>${p.owner.name}</td> 
  <td>${p.memberNames}</td> 
</tr>
</c:forEach>

</tbody>
</table>

<form method='get'>
<select name='item'>
  <option value='0' ${param.item == "0" ? "selected" : ""}>전체</option>
  <option value='1' ${param.item == "1" ? "selected" : ""}>프로젝트명</option>
  <option value='2' ${param.item == "2" ? "selected" : ""}>관리자</option>
  <option value='3' ${param.item == "3" ? "selected" : ""}>팀원</option>
</select>
<input type='search' name='keyword' value='${param.keyword}'> 
<button>검색</button>
</form>

<form method='get'>
<fieldset>
  <legend>상세 검색</legend>
  <table border='1'>
  <tbody>
  <tr> <th>프로젝트명</th> <td><input type='search' name='title' value='${param.title}'></td> </tr>
  <tr> <th>관리자</th> <td><input type='search' name='owner' value='${param.owner}'></td> </tr>
  <tr> <th>팀원</th> <td><input type='search' name='member' value='${param.member}'></td> </tr>
  <tr> <td colspan='2'><button>검색</button></td> </tr>
  </tbody>
  </table>
</fieldset>
</form>
</body>
</html>
  