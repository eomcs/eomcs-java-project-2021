<%@page import="com.eomcs.pms.domain.Member"%>
<%@page import="java.util.List"%>
<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<jsp:useBean id="members" type="List<Member>" scope="request"/>
<jsp:useBean id="projectMembers" type="List<Member>" class="java.util.ArrayList" scope="request"/>
<%
for (Member m : members) {
  String checked = "";
  if (projectMembers != null && projectMembers.size() > 0) {
    for (Member projectMember : projectMembers) {
      if (m.getNo() == projectMember.getNo()) {
        checked = "checked";
        break;
      }
    }
  }
%> 
  <input type='checkbox' name='member' value='<%=m.getNo()%>' <%=checked%>><%=m.getName()%><br>
<%
}
%>