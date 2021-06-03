package com.eomcs.pms.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;

@Controller
@RequestMapping("/project/")
@SessionAttributes({"title","content","startDate","endDate"})
public class ProjectController {

  MemberService memberService;
  ProjectService projectService;

  public ProjectController(MemberService memberService, ProjectService projectService) {
    this.memberService = memberService;
    this.projectService = projectService;
  }

  @GetMapping("form1")
  public void form1() throws Exception {
  }

  @PostMapping("form2")
  public void form2(String title, Model model) throws Exception {
    model.addAttribute("title", title);
  }

  @PostMapping("form3")
  public void form3(
      String content, 
      Date startDate, 
      Date endDate, 
      Model model) throws Exception {
    model.addAttribute("content", content);
    model.addAttribute("startDate", startDate);
    model.addAttribute("endDate", endDate);

    model.addAttribute("members", memberService.list(null));
  }

  @PostMapping("add")
  public String add(
      int[] memberNos,
      @ModelAttribute("title") String title,
      @ModelAttribute("content") String content,
      @ModelAttribute("startDate") Date startDate,
      @ModelAttribute("endDate") Date endDate,
      HttpSession session,
      SessionStatus status) throws Exception {

    Project p = new Project();
    p.setTitle(title);
    p.setContent(content);
    p.setStartDate(startDate);
    p.setEndDate(endDate);

    Member loginUser = (Member) session.getAttribute("loginUser");
    p.setOwner(loginUser);

    ArrayList<Member> memberList = new ArrayList<>();
    if (memberNos != null) {
      for (int memberNo : memberNos) {
        Member m = new Member();
        m.setNo(memberNo);
        memberList.add(m);
      }
    }
    p.setMembers(memberList);

    projectService.add(p);

    // 프로젝트를 등록하는 동안 잠시 세션에 보관된 값만 지운다.
    // => @SessionAttributes에 등록한 값만 지운다.
    status.setComplete();

    return "redirect:list";
  }

  @RequestMapping("delete")
  public String delete(int no, HttpSession session) throws Exception {

    Project oldProject = projectService.get(no);

    if (oldProject == null) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    }

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (oldProject.getOwner().getNo() != loginUser.getNo()) {
      throw new Exception("삭제 권한이 없습니다!");
    }

    projectService.delete(no);

    return "redirect:list";
  }

  @GetMapping("detail")
  public void detail(int no, Model model) throws Exception {

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

    model.addAttribute("project", project);
    model.addAttribute("projectMembers", project.getMembers());
    model.addAttribute("members", memberService.list(null));
  }

  @GetMapping("list")
  public void list(
      String item, String keyword, String title, String owner, String member, 
      Model model) throws Exception {

    List<Project> projects = null;

    if (item != null && keyword != null && keyword.length() > 0) {
      projects = projectService.search(item, keyword);

    } else if ((title != null && title.length() > 0) ||
        (owner != null && owner.length() > 0) ||
        (member != null && member.length() > 0)) {
      projects = projectService.search(title, owner, member);

    } else {
      projects = projectService.list();
    }

    model.addAttribute("projects", projects);
  }

  @PostMapping("update")
  public String update(Project project, int[] memberNos, HttpSession session) throws Exception {

    Project oldProject = projectService.get(project.getNo());
    if (oldProject == null) {
      throw new Exception("해당 번호의 프로젝트가 없습니다.");
    } 

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (oldProject.getOwner().getNo() != loginUser.getNo()) {
      throw new Exception("변경 권한이 없습니다!");
    }
    project.setOwner(loginUser);

    ArrayList<Member> memberList = new ArrayList<>();
    if (memberNos != null) {
      for (int memberNo : memberNos) {
        Member m = new Member();
        m.setNo(memberNo);
        memberList.add(m);
      }
    }
    project.setMembers(memberList);

    // DBMS에게 프로젝트 변경을 요청한다.
    projectService.update(project);

    return "redirect:list";
  }
}








