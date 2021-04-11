package com.eomcs.pms.service;

import java.util.List;
import com.eomcs.pms.domain.Member;

public interface MemberService {

  int add(Member member) throws Exception;

  List<Member> list() throws Exception;

  Member get(int no) throws Exception;

  int update(Member member) throws Exception;

  int delete(int no) throws Exception;

  Member search(String name) throws Exception;
}







