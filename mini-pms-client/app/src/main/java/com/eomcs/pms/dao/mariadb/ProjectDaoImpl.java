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
    // 1) 프로젝트 정보를 입력한다.
    int count = sqlSession.insert("ProjectMapper.insert", project);

    // 2) 프로젝트의 팀원 정보를 입력한다.
    for (Member member : project.getMembers()) {
      insertMember(project.getNo(), member.getNo());
    }

    return count;
  }

  @Override
  public List<Project> findByKeyword(String item, String keyword) throws Exception {

    HashMap<String,Object> params = new HashMap<>();
    params.put("item", item);
    params.put("keyword", keyword);

    return sqlSession.selectList("ProjectMapper.findByKeyword", params);
  }

  @Override
  public Project findByNo(int no) throws Exception {
    return sqlSession.selectOne("ProjectMapper.findByNo", no);
  }

  @Override
  public int update(Project project) throws Exception {
    // 1) 프로젝트 정보를 변경한다.
    int count = sqlSession.update("ProjectMapper.update", project);

    // 2) 프로젝트의 기존 멤버를 모두 삭제한다.
    deleteMembers(project.getNo());

    // 3) 프로젝트 멤버를 추가한다.
    for (Member member : project.getMembers()) {
      insertMember(project.getNo(), member.getNo());
    }

    return count;
  }

  @Override
  public int delete(int no) throws Exception {
    // 1) 프로젝트에 소속된 팀원 정보 삭제
    deleteMembers(no);

    // 2) 프로젝트 삭제
    return sqlSession.delete("ProjectMapper.delete", no);
  }

  @Override
  public int insertMember(int projectNo, int memberNo) throws Exception {
    HashMap<String,Object> params = new HashMap<>();
    params.put("projectNo", projectNo);
    params.put("memberNo", memberNo);
    return sqlSession.insert("ProjectMapper.insertMember", params);
  }

  @Override
  public List<Member> findAllMembers(int projectNo) throws Exception {
    return sqlSession.selectList("ProjectMapper.findAllMembers", projectNo);
  }

  @Override
  public int deleteMembers(int projectNo) throws Exception {
    return sqlSession.delete("ProjectMapper.deleteMembers", projectNo);
  }
}












