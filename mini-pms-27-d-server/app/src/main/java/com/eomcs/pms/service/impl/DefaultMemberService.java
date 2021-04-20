package com.eomcs.pms.service.impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

public class DefaultMemberService implements MemberService {

  SqlSession sqlSession;
  MemberDao memberDao;

  public DefaultMemberService(SqlSession sqlSession, MemberDao memberDao) {
    this.sqlSession = sqlSession;
    this.memberDao = memberDao;
  }  

  // 등록 업무
  @Override
  public int add(Member member) throws Exception {
    int count = memberDao.insert(member);
    sqlSession.commit();
    return count;
  }

  // 조회 업무
  @Override
  public List<Member> list() throws Exception {
    return memberDao.findAll();
  }

  // 상세 조회 업무
  @Override
  public Member get(int no) throws Exception {
    return memberDao.findByNo(no);
  }

  // 변경 업무
  @Override
  public int update(Member member) throws Exception {
    int count = memberDao.update(member);
    sqlSession.commit();
    return count;
  }

  // 삭제 업무
  @Override
  public int delete(int no) throws Exception {
    int count = memberDao.delete(no);
    sqlSession.commit();
    return count;
  }

  // 이름으로 찾기
  @Override
  public Member search(String name) throws Exception {
    return memberDao.findByName(name);
  }
}







