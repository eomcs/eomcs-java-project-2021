package com.eomcs.pms.service;

import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;

public interface ProjectService {

  int add(Project project) throws Exception;

  List<Project> list() throws Exception;

  Project get(int no) throws Exception;

  int update(Project project) throws Exception;

  int delete(int no) throws Exception;

  List<Project> search(String title, String owner, String member) throws Exception;

  List<Project> search(String item, String keyword) throws Exception;

  List<Member> getMembers(int projectNo) throws Exception;

  int deleteMembers(int projectNo) throws Exception;

  int updateMembers(int projectNo, List<Member> members) throws Exception;
}







