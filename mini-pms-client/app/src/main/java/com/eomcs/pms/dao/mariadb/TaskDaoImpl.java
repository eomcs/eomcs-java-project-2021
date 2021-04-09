package com.eomcs.pms.dao.mariadb;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.domain.Task;

public class TaskDaoImpl implements TaskDao {

  SqlSession sqlSession;

  public TaskDaoImpl(SqlSession sqlSession) throws Exception {
    this.sqlSession = sqlSession;
  }

  @Override
  public int insert(Task task) throws Exception {
    return sqlSession.insert("TaskMapper.insert", task);
  }

  @Override
  public List<Task> findAll() throws Exception {
    return sqlSession.selectList("TaskMapper.findAll");
  }

  @Override
  public List<Task> findByProjectNo(int projectNo) throws Exception {
    return sqlSession.selectList("TaskMapper.findByProjectNo", projectNo);
  }

  @Override
  public Task findByNo(int no) throws Exception {
    return sqlSession.selectOne("TaskMapper.findByNo", no);
  }

  @Override
  public int update(Task task) throws Exception {
    return sqlSession.update("TaskMapper.update", task);
  }

  @Override
  public int delete(int no) throws Exception {
    return sqlSession.delete("TaskMapper.delete", no);
  }

  @Override
  public int deleteByProjectNo(int projectNo) throws Exception {
    return sqlSession.delete("TaskMapper.deleteByProjectNo", projectNo);
  }
}












