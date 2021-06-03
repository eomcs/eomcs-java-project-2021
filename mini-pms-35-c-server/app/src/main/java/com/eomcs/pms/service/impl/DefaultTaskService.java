package com.eomcs.pms.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.TaskService;

@Service
public class DefaultTaskService implements TaskService {

  // 비즈니스 로직을 수행하는 동안 데이터 처리를 위해 사용할 DAO 를 주입 받아야 한다.
  TaskDao taskDao; 

  public DefaultTaskService(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  // 등록 업무
  @Override
  public int add(Task task) throws Exception {
    return taskDao.insert(task);
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
    return taskDao.update(task);
  }

  // 삭제 업무
  @Override
  public int delete(int no) throws Exception {
    return taskDao.delete(no);
  }

}







