package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Project;

public abstract class AbstractProjectHandler implements Command {

  protected List<Project> projectList;

  public AbstractProjectHandler(List<Project> projectList) {
    this.projectList = projectList;
  }

  protected Project findByNo(int projectNo) {
    Project[] list = projectList.toArray(new Project[projectList.size()]);
    for (Project p : list) {
      if (p.getNo() == projectNo) {
        return p;
      }
    }
    return null;
  }

}








