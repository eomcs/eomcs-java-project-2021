package com.eomcs.pms.dao.mariadb;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;

public class ProjectDaoImpl implements ProjectDao {

  SqlSession sqlSession;

  public ProjectDaoImpl(SqlSession sqlSession) throws Exception {
    this.sqlSession = sqlSession;
  }

  @Override
  public int insert(Project project) throws Exception {
    try {
      // 1) 프로젝트 정보를 입력한다.
      int count = sqlSession.insert("ProjectMapper.insert", project);

      // 일부로 예외를 발생시킨다.
      // => 그러면 프로젝트 멤버는 등록되지 않을 것이다.
      // => 자동 커밋 모드
      //    - 그럼에도 프로젝트 정보는 등록된 상태로 있을 것이다.
      // => 수동 커밋 모드
      //    - 이전에 수행한 작업을 취소하기 때문에 프로젝트 정보는 등록되지 않는다.
      if (count > 0) {
        throw new Exception("프로젝트 등록 후 일부로 예외 발생!");
      }

      // 2) 멤버를 입력한다.
      insertMembers(project.getNo(), project.getMembers());

      sqlSession.commit();
      return count;

    } catch (Exception e) {
      // 이전에 성공한 작업이 있으면 모두 취소한다.
      sqlSession.rollback();

      // 예외 상황을 이 메서드의 호출자에게 알려야 한다.
      throw e;
    }
  }

  @Override
  public List<Project> findByKeyword(String item, String keyword) throws Exception {

    HashMap<String,Object> params = new HashMap<>();
    params.put("item", item);
    params.put("keyword", keyword);

    return sqlSession.selectList("ProjectMapper.findByKeyword", params);
  }

  @Override
  public List<Project> findByKeywords(String title, String owner, String member) throws Exception {

    HashMap<String,Object> params = new HashMap<>();
    params.put("title", title);
    params.put("owner", owner);
    params.put("member", member);

    return sqlSession.selectList("ProjectMapper.findByKeywords", params);
  }

  @Override
  public Project findByNo(int no) throws Exception {
    return sqlSession.selectOne("ProjectMapper.findByNo", no);
  }

  @Override
  public int update(Project project) throws Exception {
    try {
      // 1) 프로젝트 정보를 변경한다.
      int count = sqlSession.update("ProjectMapper.update", project);

      // 2) 프로젝트의 기존 멤버를 모두 삭제한다.
      deleteMembers(project.getNo());

      // 3) 프로젝트 멤버를 추가한다.
      insertMembers(project.getNo(), project.getMembers());

      sqlSession.commit();
      return count;

    } catch (Exception e) {
      sqlSession.rollback();

      throw e;
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try {
      // 1) 프로젝트에 소속된 팀원 정보 삭제
      deleteMembers(no);

      // 2) 프로젝트 삭제
      int count = sqlSession.delete("ProjectMapper.delete", no);

      if (count > 0) {
        throw new Exception("프로젝트 멤버 삭제 후 일부러 예외 발생!");
      }

      sqlSession.commit();
      return count;

    } catch (Exception e) {
      sqlSession.rollback();
      throw e;
    }
  }

  @Override
  public int insertMember(int projectNo, int memberNo) throws Exception {
    HashMap<String,Object> params = new HashMap<>();
    params.put("projectNo", projectNo);
    params.put("memberNo", memberNo);
    int count = sqlSession.insert("ProjectMapper.insertMember", params);
    sqlSession.commit();
    return count;
  }

  @Override
  public int insertMembers(int projectNo, List<Member> members) throws Exception {
    try {
      HashMap<String,Object> params = new HashMap<>();
      params.put("projectNo", projectNo);
      params.put("members", members);
      int count = sqlSession.insert("ProjectMapper.insertMembers", params);
      if (count > 0) {
        throw new Exception("프로젝트 멤버를 등록한 후 일부러 예외 발생!");
      }
      sqlSession.commit();
      return count;

    } catch (Exception e) {
      sqlSession.rollback();
      throw e;
    }
  }

  @Override
  public List<Member> findAllMembers(int projectNo) throws Exception {
    return sqlSession.selectList("ProjectMapper.findAllMembers", projectNo);
  }

  @Override
  public int deleteMembers(int projectNo) throws Exception {
    int count = sqlSession.delete("ProjectMapper.deleteMembers", projectNo);
    sqlSession.commit();
    return count;
  }
}












