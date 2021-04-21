package com.eomcs.pms.service.impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.TaskService;

// 서비스 객체
// - 비즈니스 로직을 담고 있다.
// - 업무에 따라 트랜잭션을 제어하는 일을 한다.
// - 서비스 객체의 메서드는 가능한 업무 관련 용어를 사용하여 메서드를 정의한다.
//
public class DefaultTaskService implements TaskService {

  SqlSessionFactory sqlSessionFactory;

  // 비즈니스 로직을 수행하는 동안 데이터 처리를 위해 사용할 DAO 를 주입 받아야 한다.
  TaskDao taskDao; 

  public DefaultTaskService(SqlSessionFactory sqlSessionFactory, TaskDao taskDao) {
    this.sqlSessionFactory = sqlSessionFactory;
    this.taskDao = taskDao;
  }

  // 등록 업무
  @Override
  public int add(Task task) throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    int count = taskDao.insert(task);
    sqlSession.commit();
    return count;
  }

  // 조회 업무
  @Override
  public List<Task> list() throws Exception {
    return taskDao.findAll();
  }

  @Override
  public List<Task> listOfProject(int projectNo) throws Exception {
    return taskDao.findByProjectNo(projectNo);
  }

  // 상세 조회 업무
  @Override
  public Task get(int no) throws Exception {
    return taskDao.findByNo(no);
  }

  // 변경 업무
  @Override
  public int update(Task task) throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    int count = taskDao.update(task);
    sqlSession.commit();
    return count;
  }

  // 삭제 업무
  @Override
  public int delete(int no) throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    int count = taskDao.delete(no);
    sqlSession.commit();
    return count;
  }

}







