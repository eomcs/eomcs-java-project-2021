package com.eomcs.pms.handler;

import java.util.Arrays;
import com.eomcs.pms.domain.Project;

public class ProjectList {
  static final int DEFAULT_CAPACITY = 100;
  Project[] projects = new Project[DEFAULT_CAPACITY];
  int size = 0;

  void add(Project p) {
    if (this.size == this.projects.length) {
      projects = Arrays.copyOf(this.projects, this.size + (this.size >> 1));
    }
    this.projects[this.size++] = p;
  }

  Project[] toArray() {
    Project[] arr = new Project[this.size];
    for (int i = 0; i < this.size; i++) {
      arr[i] = this.projects[i];
    }
    return arr;
  }

  Project get(int projectNo) {
    int i = indexOf(projectNo);
    if (i == -1)
      return null;
    return projects[i];
  }

  void delete(int projectNo) {
    int index = indexOf(projectNo);

    if (index == -1)
      return;

    for (int x = index + 1; x < this.size; x++) {
      this.projects[x-1] = this.projects[x];
    }
    this.projects[--this.size] = null; 
  }

  int indexOf(int projectNo) {
    for (int i = 0; i < this.size; i++) {
      Project project = this.projects[i];
      if (project.no == projectNo) {
        return i;
      }
    }
    return -1;
  }
}
