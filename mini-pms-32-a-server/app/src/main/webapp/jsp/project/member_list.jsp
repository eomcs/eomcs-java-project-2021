<%@page import="com.eomcs.pms.domain.Member"%>
<%@page import="java.util.List"%>
<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%
List<Member> members = (List<Member>) request.getAttribute("members");
List<Member> projectMembers = (List<Member>) request.getAttribute("projectMembers");

for (Member m : members) {
  String checked = "";
  if (projectMembers != null) {
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