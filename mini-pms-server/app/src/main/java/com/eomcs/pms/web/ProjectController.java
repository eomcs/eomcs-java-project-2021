package com.eomcs.pms.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;

@Controller
@RequestMapping("/project/")
public class ProjectController {

  MemberService memberService;
  ProjectService projectService;

  public ProjectController(MemberService memberService, ProjectService projectService) {
    this.memberService = memberService;
    this.projectService = projectService;
  }

  @GetMapping("add1")
  public String add1(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/jsp/project/form1.jsp";
  }

  @PostMapping("add2")
  public String add2(HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession();
    session.setAttribute("title", request.getParameter("title"));
    return "/jsp/project/form2.jsp";
  }

  @PostMapping("add3")
  public String add3(HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession();
    session.setAttribute("content", request.getParameter("content"));
    session.setAttribute("startDate", request.getParameter("startDate"));
    session.setAttribute("endDate", request.getParameter("endDate"));

    List<Member> members = memberService.list(null);
    request.setAttribute("members", members);
    return "/jsp/project/form3.jsp";
  }

  @PostMapping("add")
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {

    HttpSession session = request.getSession();

    Project p = new Project();
    p.setTitle((String) session.getAttribute("title"));
    p.setContent((String) session.getAttribute("content"));
    p.setStartDate(Date.valueOf((String) session.getAttribute("startDate")));
    p.setEndDate(Date.valueOf((String) session.getAttribute("endDate")));

    Member loginUser = (Member) session.getAttribute("loginUser");
    p.setOwner(loginUser);

    String[] values = request.getParameterValues("member");
    ArrayList<Member> memberList = new ArrayList<>();
    if (values != null) {
      for (String value : values) {
        Member member = new Member();
        member.setNo(Integer.parseInt(value));
        memberList.add(member);
      }
    }
    p.setMembers(memberList);

    projectService.add(p);

    return "redirect:list";
  }

  @RequestMapping("delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Project oldProject = projectService.get(no);

    if (oldProject == null) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    }

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (oldProject.getOwner().getNo() != loginUser.getNo()) {
      throw new Exception("삭제 권한이 없습니다!");
    }

    projectService.delete(no);

    return "redirect:list";
  }

  @GetMapping("detail")
  public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Project project = projectService.get(no);
    if (project == null) {
      throw new Exception("해당 번호의 프로젝트가 없습니다.");
    }

    //      MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");
    //      List<Member> members = memberService.list(null);
    //      for (Member m : members) {
    //        out.printf("  <input type='checkbox' name='member' value='%d' %s>%s<br>\n", 
    //            m.getNo(), contain(project.getMembers(), m.getNo()) ? "checked" : "", m.getName());
    //      }

    request.setAttribute("project", project);
    request.setAttribute("projectMembers", project.getMembers());
    request.setAttribute("members", memberService.list(null));
    return "/jsp/project/detail.jsp";
  }

  @GetMapping("list")
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {

    List<Project> projects = null;

    String item = request.getParameter("item");
    String keyword = request.getParameter("keyword");
    String title = request.getParameter("title");
    String owner = request.getParameter("owner");
    String member = request.getParameter("member");

    if (item != null && keyword != null && keyword.length() > 0) {
      projects = projectService.search(item, keyword);

    } else if ((title != null && title.length() > 0) ||
        (owner != null && owner.length() > 0) ||
        (member != null && member.length() > 0)) {
      projects = projectService.search(title, owner, member);

    } else {
      projects = projectService.list();
    }

    request.setAttribute("projects", projects);
    return "/jsp/project/list.jsp";
  }

  @PostMapping("update")
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Project oldProject = projectService.get(no);

    if (oldProject == null) {
      throw new Exception("해당 번호의 프로젝트가 없습니다.");
    } 

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (oldProject.getOwner().getNo() != loginUser.getNo()) {
      throw new Exception("변경 권한이 없습니다!");
    }

    // 사용자에게서 변경할 데이터를 입력 받는다.
    Project project = new Project();
    project.setNo(no);
    project.setTitle(request.getParameter("title"));
    project.setContent(request.getParameter("content"));
    project.setStartDate(Date.valueOf(request.getParameter("startDate")));
    project.setEndDate(Date.valueOf(request.getParameter("endDate")));
    project.setOwner(loginUser);

    // ...&member=1&member=18&member=23
    String[] values = request.getParameterValues("member");
    ArrayList<Member> memberList = new ArrayList<>();
    if (values != null) {
      for (String value : values) {
        Member member = new Member();
        member.setNo(Integer.parseInt(value));
        memberList.add(member);
      }
    }
    project.setMembers(memberList);

    // DBMS에게 프로젝트 변경을 요청한다.
    projectService.update(project);

    return "redirect:list";
  }
}








