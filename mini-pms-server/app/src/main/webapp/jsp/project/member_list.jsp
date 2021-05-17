<%@page import="com.eomcs.pms.domain.Member"%>
<%@page import="java.util.List"%>
<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%! 
// declaration element:
// - 이 엘리먼트에 작성한 자바 코드는 서블릿 클래스를 만들 때 클래스 블록 안에 그대로 복사된다.
//   즉 _jspService() 메서드의 안이 아니라 밖에 복사된다.
//   그래서 이 엘리먼트에는 클래스에 삽입할 필드나 메서드를 정의하는 코드를 둔다.
String checked(List<Member> members, int memberNo) {
  if (members == null || members.size() == 0) {
    return "";
  }
  
  for (Member m : members) {
    if (m.getNo() == memberNo) {
      return "checked";
    }
  }
  return "";
}
%>
<jsp:useBean id="members" type="List<Member>" scope="request"/>
<jsp:useBean id="projectMembers" type="List<Member>" class="java.util.ArrayList" scope="request"/>
<%
for (Member m : members) {
  pageContext.setAttribute("m", m);
%> 
  <input type='checkbox' name='member' 
         value='${m.no}' <%=checked(projectMembers, m.getNo())%>>${m.name}<br>
<%
}
%>