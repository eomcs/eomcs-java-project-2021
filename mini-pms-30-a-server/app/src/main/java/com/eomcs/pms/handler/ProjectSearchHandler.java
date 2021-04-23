package com.eomcs.pms.handler;

import java.io.PrintWriter;
import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/project/search")
public class ProjectSearchHandler implements Command {

  ProjectService projectService;

  public ProjectSearchHandler(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[프로젝트 검색]");

    String item = prompt.inputString("항목(1:프로젝트명, 2:관리자명, 3:팀원, 그 외: 전체)? ");
    String keyword = null;
    if (item.equals("1") || 
        item.equals("2") || 
        item.equals("3")) {
      keyword = prompt.inputString("검색어? ");
    }

    List<Project> projects = projectService.search(item, keyword);

    for (Project p : projects) {

      // 1) 프로젝트의 팀원 목록 가져오기
      StringBuilder strBuilder = new StringBuilder();
      List<Member> members = p.getMembers();
      for (Member m : members) {
        if (strBuilder.length() > 0) {
          strBuilder.append("/");
        }
        strBuilder.append(m.getName());
      }

      // 2) 프로젝트 정보를 출력
      out.printf("%d, %s, %s, %s, %s, [%s]\n", 
          p.getNo(), 
          p.getTitle(), 
          p.getStartDate(),
          p.getEndDate(),
          p.getOwner().getName(),
          strBuilder.toString());
    }
  }
}








