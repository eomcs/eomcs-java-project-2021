package com.eomcs.pms.web.listener;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
import com.eomcs.pms.service.impl.DefaultBoardService;
import com.eomcs.pms.service.impl.DefaultMemberService;
import com.eomcs.pms.service.impl.DefaultProjectService;
import com.eomcs.pms.service.impl.DefaultTaskService;
import com.eomcs.util.Component;

// 웹 애플리케이션을 시작하거나 종료할 때 서버로부터 보고를 받는다.
// 즉 톰캣 서버가 웹 애플리케이션을 시작하거나 종료하면 리스너 규칙에 따라 메서드를 호출한다는 뜻이다.
// 
public class ContextLoaderListener implements ServletContextListener {

  // 객체를 보관할 컨테이너 준비
  // - object = bean = instance 
  Map<String,Object> beanContainer = new HashMap<>();

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

      // 4) 서비스 객체를 빈 컨테이너에 저장한다.
      beanContainer.put("boardService", new DefaultBoardService(boardDao));
      beanContainer.put("memberService", new DefaultMemberService(memberDao));
      beanContainer.put("projectService", new DefaultProjectService(txManager, projectDao, taskDao));
      beanContainer.put("taskService", new DefaultTaskService(taskDao));

      // 5) @Component 객체를 준비한다.
      registerComponents();

      // 6) 프론트 컨트롤러가 @Component 객체를 사용할 수 있도록 beanContainer를 
      // ServletContext에 보관한다.
      servletContext.setAttribute("beanContainer", beanContainer);

      System.out.println("ContextLoaderListener: 의존 객체를 모두 준비하였습니다.");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void registerComponents() throws Exception {

    // 지정된 패키지에서 @Component 애노테이션이 붙은 클래스를 로딩한다.
    ArrayList<Class<?>> components = new ArrayList<>();
    loadComponents("com.eomcs.pms.web", components);

    // @Component 목록에서 클래스 정보를 한 개 꺼내 컴포넌트를 생성하여 빈컨테이너에 저장한다.
    for (Class<?> clazz : components) {
      registerComponent(clazz);
    }
  }

  private void loadComponents(String packageName, ArrayList<Class<?>> components) throws Exception {

    // 패키지의 '파일 시스템 경로'를 알아낸다.
    File dir = Resources.getResourceAsFile(packageName.replaceAll("\\.", "/"));

    if (!dir.isDirectory()) {
      throw new Exception("유효한 패키지가 아닙니다.");
    }

    File[] files = dir.listFiles(f -> {
      if (f.isDirectory() || f.getName().endsWith(".class"))
        return true;
      return false;
    });

    for (File file : files) {
      if (file.isDirectory()) {
        loadComponents(packageName + "." + file.getName(), components);
      } else {
        String className = packageName + "." + file.getName().replace(".class", "");
        try {
          Class<?> clazz = Class.forName(className);
          if (clazz.getAnnotation(Component.class) != null) {
            components.add(clazz);
          }
        } catch (Exception e) {
          System.out.println("클래스 로딩 오류: " + className);
        }
      }
    }
  }

  private void registerComponent(Class<?> clazz) throws Exception {
    // 클래스 정보를 이용하여 인스턴스를 생성한다.
    Object command = createObject(clazz);

    // 클래스 정보에서 @Component 애노테이션 정보를 가져온다.
    Component compAnno = clazz.getAnnotation(Component.class);

    // 애노테이션 정보에서 맵에 객체를 저장할 때 키로 사용할 문자열 꺼낸다.
    String key = null;
    if (compAnno.value().length() == 0){
      key = clazz.getName(); // 키로 사용할 문자열이 없으면 클래스 이름을 키로 사용한다.
    } else {
      key = compAnno.value();
    }

    // 생성된 객체를 객체 맵에 보관한다.
    beanContainer.put(key, command);

    System.out.println("컴포넌트 생성 ===> " + command.getClass().getName());
  }

  private Object createObject(Class<?> clazz) throws Exception {
    // 생성자 정보를 알아낸다. 첫 번째 생성자만 꺼낸다.
    Constructor<?> constructor = clazz.getConstructors()[0];

    // 생성자의 파라미터 정보를 알아낸다.
    Parameter[] params = constructor.getParameters();

    // 생성자를 호출할 때 넘겨 줄 값을 담을 컬렉션을 준비한다.
    ArrayList<Object> args = new ArrayList<>();

    // 각 파라미터의 타입을 알아낸 후 objMap에서 찾는다.
    for (Parameter p : params) {
      Class<?> paramType = p.getType();
      args.add(findDependency(paramType));
    }

    // 생성자를 호출하여 인스턴스를 생성한다.
    return constructor.newInstance(args.toArray());
  }

  private Object findDependency(Class<?> type) {
    // 맵에서 값 목록을 꺼낸다.
    Collection<?> values = beanContainer.values();

    for (Object obj : values) {
      if (type.isInstance(obj)) {
        return obj;
      }
    }
    return null;
  }

}
