package com.eomcs.pms.service.impl;

import java.util.HashMap;
import java.util.List;
import com.eomcs.mybatis.TransactionManager;
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
public class DefaultProjectService01 implements ProjectService {

  TransactionManager txManager;

  ProjectDao projectDao;
  TaskDao taskDao;

  public DefaultProjectService01(TransactionManager txManager, ProjectDao projectDao, TaskDao taskDao) {
    this.txManager = txManager;
    this.projectDao = projectDao;
    this.taskDao = taskDao;
  }

  // 등록 업무 
  @Override
  public int add(Project project) throws Exception {
    txManager.beginTransaction();
    try {
      // 1) 프로젝트 정보를 입력한다.
      int count = projectDao.insert(project);

      // 2) 멤버를 입력한다.
      HashMap<String,Object> params = new HashMap<>();
      params.put("projectNo", project.getNo());
      params.put("members", project.getMembers());

      projectDao.insertMembers(params);

      txManager.commit();
      return count;

    } catch (Exception e) {
      txManager.rollback();
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
    txManager.beginTransaction();
    try {
      int count = projectDao.update(project);
      projectDao.deleteMembers(project.getNo());

      HashMap<String,Object> params = new HashMap<>();
      params.put("projectNo", project.getNo());
      params.put("members", project.getMembers());

      projectDao.insertMembers(params);

      // 다른 스레드가 rollback 할 수 있도록 잠시 대기한다.
      Thread.sleep(30000);

      txManager.commit();
      return count;

    } catch (Exception e) {
      txManager.rollback();
      throw e;
    }
  }

  // 삭제 업무
  @Override
  public int delete(int no) throws Exception {
    txManager.beginTransaction();
    try {
      // 1) 프로젝트의 모든 작업 삭제
      taskDao.deleteByProjectNo(no);

      // 2) 프로젝트 멤버 삭제
      projectDao.deleteMembers(no);

      // 롤백하기 위해 일부러 예외 발생!
      if (no != 1000) {
        throw new Exception("프로젝트 삭제 중 오류 발생!");
      }

      // 3) 프로젝트 삭제
      int count = projectDao.delete(no);
      txManager.commit();
      return count;

    } catch (Exception e) {
      txManager.rollback();
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
    return projectDao.deleteMembers(projectNo);
  }

  @Override
  public int updateMembers(int projectNo, List<Member> members) throws Exception {
    txManager.beginTransaction();
    try {
      projectDao.deleteMembers(projectNo);

      HashMap<String,Object> params = new HashMap<>();
      params.put("projectNo", projectNo);
      params.put("members", members);

      int count = projectDao.insertMembers(params);
      txManager.commit();
      return count;

    } catch (Exception e) {
      txManager.rollback();
      throw e;
    }
  }
}







