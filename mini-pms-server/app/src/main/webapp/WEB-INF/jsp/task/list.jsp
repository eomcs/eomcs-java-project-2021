<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
  trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>작업</title>
</head>
<body>
<h1>작업(JSP + JSP 액션태그 + EL + JSTL)</h1>
<p><a href='form'>새 작업</a></p>
<form>
프로젝트: <select name='projectNo'>
  <option value='0'>전체</option>
  
<c:forEach items="${projects}" var="p">
  <option value='${p.no}'  ${p.no == projectNo ? "selected" : ""}>${p.title}</option>
</c:forEach>

</select>
<button>검색</button>
</form>
<table border='1'>
<thead>
<tr>
<th>번호</th> <th>작업</th> <th>마감일</th> <th>담당자</th> <th>상태</th>
</tr>
</thead>
<tbody>
<c:if test="${empty tasks}">
	<tr>
	  <td colspan='5'>해당 번호의 프로젝트가 없거나 또는 등록된 작업이 없습니다.</td>
	</tr>
</c:if>

<c:if test="${not empty tasks}">
  <c:forEach items="${tasks}" var="t">
	  <c:if test="${projectNo != t.projectNo}">
		<tr>
		  <td colspan='5'>프로젝트: '${t.projectTitle}'</td>
		</tr>
		<c:set var="projectNo" value="${t.projectNo}"/>
	  </c:if>

		<tr> 
		  <td>${t.no}</td> 
		  <td><a href='detail?no=${t.no}'>${t.content}</a></td> 
		  <td>${t.deadline}</td> 
		  <td>${t.owner.name}</td> 
		  <td>
		    <c:choose>
		      <c:when test="${t.status == 1}">진행중</c:when>
		      <c:when test="${t.status == 2}">완료</c:when>
		      <c:otherwise>신규</c:otherwise>
		    </c:choose>
		  </td> 
		</tr>

  </c:forEach>
</c:if>
</tbody>
</table>
</body>
</html>
 