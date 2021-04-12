package com.eomcs.pms.service.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

// 서비스 객체
// - 비즈니스 로직을 담고 있다.
// - 업무에 따라 트랜잭션을 제어하는 일을 한다.
// - 서비스 객체의 메서드는 가능한 업무 관련 용어를 사용하여 메서드를 정의한다.
//
public class DefaultProjectService implements ProjectService {

  // 서비스 객체는 트랜잭션을 제어해야 하기 때문에
  // DAO가 사용하는 SqlSession 객체를 주입 받아야 한다.
  SqlSession sqlSession; 

  // 비즈니스 로직을 수행하는 동안 데이터 처리를 위해 사용할 DAO 를 주입 받아야 한다.
  ProjectDao projectDao;
  TaskDao taskDao;

  public DefaultProjectService(SqlSession sqlSession, ProjectDao projectDao, TaskDao taskDao) {
    this.sqlSession = sqlSession;
    this.projectDao = projectDao;
    this.taskDao = taskDao;
  }

  // 등록 업무 
  @Override
  public int add(Project project) throws Exception {
    try {
      // 1) 프로젝트 정보를 입력한다.
      int count = projectDao.insert(project);

      // 2) 멤버를 입력한다.
      HashMap<String,Object> params = new HashMap<>();
      params.put("projectNo", project.getNo());
      params.put("members", project.getMembers());

      projectDao.insertMembers(params);

      sqlSession.commit();
      return count;

    } catch (Exception e) {
      sqlSession.rollback();
      throw e;
    }
  }

  // 조회 업무
  @Override
  public List<Project> list() throws Exception {
    return projectDao.findByKeyword(null);
  }

  // 상세 조회 업무
  @Override
  public Project get(int no) throws Exception {
    return projectDao.findByNo(no);
  }

  // 변경 업무
  @Override
  public int update(Project project) throws Exception {
    try {
      int count = projectDao.update(project);
      projectDao.deleteMembers(project.getNo());

      HashMap<String,Object> params = new HashMap<>();
      params.put("projectNo", project.getNo());
      params.put("members", project.getMembers());

      projectDao.insertMembers(params);

      sqlSession.commit();
      return count;

    } catch (Exception e) {
      sqlSession.rollback();
      throw e;
    }
  }

  // 삭제 업무
  @Override
  public int delete(int no) throws Exception {
    try {
      // 1) 프로젝트의 모든 작업 삭제
      taskDao.deleteByProjectNo(no);

      // 2) 프로젝트 멤버 삭제
      projectDao.deleteMembers(no);

      // 3) 프로젝트 삭제
      int count = projectDao.delete(no);
      sqlSession.commit();
      return count;

    } catch (Exception e) {
      sqlSession.rollback();
      throw e;
    }
  }

  // 찾기
  @Override
  public List<Project> search(String title, String owner, String member) throws Exception {
    HashMap<String,Object> params = new HashMap<>();
    params.put("title", title);
    params.put("owner", owner);
    params.put("member", member);

    return projectDao.findByKeywords(params);
  }

  @Override
  public List<Project> search(String item, String keyword) throws Exception {
    HashMap<String,Object> params = new HashMap<>();
    params.put("item", item);
    params.put("keyword", keyword);

    return projectDao.findByKeyword(params);
  }

  @Override
  public int deleteMembers(int projectNo) throws Exception {
    int count = projectDao.deleteMembers(projectNo);
    sqlSession.commit();
    return count;
  }

  @Override
  public int updateMembers(int projectNo, List<Member> members) throws Exception {
    try {
      projectDao.deleteMembers(projectNo);

      HashMap<String,Object> params = new HashMap<>();
      params.put("projectNo", projectNo);
      params.put("members", members);

      int count = projectDao.insertMembers(params);
      sqlSession.commit();
      return count;

    } catch (Exception e) {
      sqlSession.rollback();
      throw e;
    }
  }
}







