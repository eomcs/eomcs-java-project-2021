package com.eomcs.pms.dao;

import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;

public interface ProjectDao {

  int insert(Project project) throws Exception;

  List<Project> findAll() throws Exception;

  Project findByNo(int no) throws Exception;

  int update(Project project) throws Exception;

  int delete(int no) throws Exception;

  int insertMember(int projectNo, int memberNo) throws Exception;

  List<Member> findAllMembers(int projectNo) throws Exception;

  int deleteMembers(int projectNo) throws Exception;
}












