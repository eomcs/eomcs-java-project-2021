package com.eomcs.pms.web.listener;

import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.MybatisDaoFactory;
import com.eomcs.mybatis.SqlSessionFactoryProxy;
import com.eomcs.mybatis.TransactionManager;
import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.service.BoardService;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;
import com.eomcs.pms.service.impl.DefaultBoardService;
import com.eomcs.pms.service.impl.DefaultMemberService;
import com.eomcs.pms.service.impl.DefaultProjectService;
import com.eomcs.pms.service.impl.DefaultTaskService;

// 웹 애플리케이션을 시작하거나 종료할 때 서버로부터 보고를 받는다.
// 즉 톰캣 서버가 웹 애플리케이션을 시작하거나 종료하면 리스너 규칙에 따라 메서드를 호출한다는 뜻이다.
// 
public class ContextLoaderListener implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    try {
      ServletContext servletContext = sce.getServletContext();

      // 1) Mybatis 관련 객체 준비
      InputStream mybatisConfigStream = Resources.getResourceAsStream(
          servletContext.getInitParameter("mybatis-config"));
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(mybatisConfigStream);
      SqlSessionFactoryProxy sqlSessionFactoryProxy = new SqlSessionFactoryProxy(sqlSessionFactory);

      // 2) DAO 관련 객체 준비
      MybatisDaoFactory daoFactory = new MybatisDaoFactory(sqlSessionFactoryProxy);
      BoardDao boardDao = daoFactory.createDao(BoardDao.class);
      MemberDao memberDao = daoFactory.createDao(MemberDao.class);
      ProjectDao projectDao = daoFactory.createDao(ProjectDao.class);
      TaskDao taskDao = daoFactory.createDao(TaskDao.class);

      // 3) 서비스 관련 객체 준비
      TransactionManager txManager = new TransactionManager(sqlSessionFactoryProxy);

      BoardService boardService = new DefaultBoardService(boardDao);
      MemberService memberService = new DefaultMemberService(memberDao);
      ProjectService projectService = new DefaultProjectService(txManager, projectDao, taskDao);
      TaskService taskService = new DefaultTaskService(taskDao);

      // 4) 서비스 객체를 ServletContext 보관소에 저장한다.
      servletContext.setAttribute("boardService", boardService);
      servletContext.setAttribute("memberService", memberService);
      servletContext.setAttribute("projectService", projectService);
      servletContext.setAttribute("taskService", taskService);

      System.out.println("ContextLoaderListener: 의존 객체를 모두 준비하였습니다.");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
