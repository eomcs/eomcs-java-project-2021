package com.eomcs.pms.handler;

import java.util.Iterator;
import java.util.List;
import com.eomcs.pms.domain.Project;

public class ProjectListHandler extends AbstractProjectHandler {

  public ProjectListHandler(List<Project> projectList) {
    super(projectList);
  }

  @Override
  public void service() {
    System.out.println("[프로젝트 목록]");

    Iterator<Project> iterator = projectList.iterator();

    while (iterator.hasNext()) {
      Project p = iterator.next();
      System.out.printf("%d, %s, %s, %s, %s, [%s]\n",
          p.getNo(), p.getTitle(), p.getStartDate(), p.getEndDate(), p.getOwner(), p.getMembers());
    }
  }
}








