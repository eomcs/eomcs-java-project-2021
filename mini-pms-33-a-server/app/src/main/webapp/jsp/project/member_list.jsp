<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${members}" var="m" >
  <c:forEach items="${projectMembers}" var="projectMember">
    <c:if test="${m.no == projectMember.no}">
      <c:set var="checked" value="checked"/>
    </c:if>
  </c:forEach>
  <input type='checkbox' name='member' value='${m.no}' ${checked}>${m.name}<br>
  <c:remove var="checked"/>
</c:forEach>