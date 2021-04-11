package com.eomcs.pms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.handler.BoardAddHandler;
import com.eomcs.pms.handler.BoardDeleteHandler;
import com.eomcs.pms.handler.BoardDetailHandler;
import com.eomcs.pms.handler.BoardListHandler;
import com.eomcs.pms.handler.BoardSearchHandler;
import com.eomcs.pms.handler.BoardUpdateHandler;
import com.eomcs.pms.handler.Command;
import com.eomcs.pms.handler.HelloHandler;
import com.eomcs.pms.handler.MemberAddHandler;
import com.eomcs.pms.handler.MemberDeleteHandler;
import com.eomcs.pms.handler.MemberDetailHandler;
import com.eomcs.pms.handler.MemberListHandler;
import com.eomcs.pms.handler.MemberUpdateHandler;
import com.eomcs.pms.handler.MemberValidatorHandler;
import com.eomcs.pms.handler.ProjectAddHandler;
import com.eomcs.pms.handler.ProjectDeleteHandler;
import com.eomcs.pms.handler.ProjectDetailHandler;
import com.eomcs.pms.handler.ProjectListHandler;
import com.eomcs.pms.handler.ProjectUpdateHandler;
import com.eomcs.pms.handler.TaskAddHandler;
import com.eomcs.pms.handler.TaskDeleteHandler;
import com.eomcs.pms.handler.TaskDetailHandler;
import com.eomcs.pms.handler.TaskListHandler;
import com.eomcs.pms.handler.TaskUpdateHandler;
import com.eomcs.pms.listener.AppListener;
import com.eomcs.pms.listener.FileListener;
import com.eomcs.util.Prompt;

public class App {

  // 옵저버 객체(ApplicationContextListener 구현체) 목록을 저장할 컬렉션 준비
  List<ApplicationContextListener> listeners = new ArrayList<>();

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  ArrayDeque<String> commandStack = new ArrayDeque<>();
  LinkedList<String> commandQueue = new LinkedList<>();

  // 옵저버와 값을 공유하기 위해 사용할 공통 저장소 객체를 준비
  Map<String,Object> appContext = new HashMap<>();

  public static void main(String[] args) {
    App app = new App();

    app.addApplicationContextListener(new AppListener());
    app.addApplicationContextListener(new FileListener());

    app.service();
  }

  public void addApplicationContextListener(ApplicationContextListener listener) {
    listeners.add(listener);
  }

  public void removeApplicationContextListener(ApplicationContextListener listener) {
    listeners.remove(listener);
  }

  @SuppressWarnings("unchecked")
  public void service() {

    notifyOnServiceStarted();

    // FileListener가 준비한 List 객체를 꺼낸다.
    List<Board> boardList = (List<Board>) appContext.get("boardList");
    List<Member> memberList = (List<Member>) appContext.get("memberList");
    List<Project> projectList = (List<Project>) appContext.get("projectList");
    List<Task> taskList = (List<Task>) appContext.get("taskList");

    // 사용자 명령을 처리하는 객체를 맵에 보관한다.
    HashMap<String,Command> commandMap = new HashMap<>();

    commandMap.put("/board/add", new BoardAddHandler(boardList));
    commandMap.put("/board/list", new BoardListHandler(boardList));
    commandMap.put("/board/detail", new BoardDetailHandler(boardList));
    commandMap.put("/board/update", new BoardUpdateHandler(boardList));
    commandMap.put("/board/delete", new BoardDeleteHandler(boardList));

    commandMap.put("/member/add", new MemberAddHandler(memberList));
    commandMap.put("/member/list", new MemberListHandler(memberList));
    commandMap.put("/member/detail", new MemberDetailHandler(memberList));
    commandMap.put("/member/update", new MemberUpdateHandler(memberList));
    commandMap.put("/member/delete", new MemberDeleteHandler(memberList));
    MemberValidatorHandler memberValidatorHandler = new MemberValidatorHandler(memberList);

    commandMap.put("/project/add", new ProjectAddHandler(projectList, memberValidatorHandler));
    commandMap.put("/project/list", new ProjectListHandler(projectList));
    commandMap.put("/project/detail", new ProjectDetailHandler(projectList));
    commandMap.put("/project/update", new ProjectUpdateHandler(projectList, memberValidatorHandler));
    commandMap.put("/project/delete", new ProjectDeleteHandler(projectList));

    commandMap.put("/task/add", new TaskAddHandler(taskList, memberValidatorHandler));
    commandMap.put("/task/list", new TaskListHandler(taskList));
    commandMap.put("/task/detail", new TaskDetailHandler(taskList));
    commandMap.put("/task/update", new TaskUpdateHandler(taskList, memberValidatorHandler));
    commandMap.put("/task/delete", new TaskDeleteHandler(taskList));

    // 새 기능 추가
    commandMap.put("/board/search", new BoardSearchHandler(boardList));
    commandMap.put("/hello", new HelloHandler());

    loop:
      while (true) {
        String command = com.eomcs.util.Prompt.inputString("명령> ");

        if (command.length() == 0) // 사용자가 빈 문자열을 입력하면 다시 입력하도록 요구한다.
          continue;

        // 사용자가 입력한 명령을 보관해둔다.
        commandStack.push(command);
        commandQueue.offer(command);

        try {
          switch (command) {
            case "history":
              printCommandHistory(commandStack.iterator());
              break;
            case "history2": 
              printCommandHistory(commandQueue.iterator());
              break;
            case "quit":
            case "exit":
              System.out.println("안녕!");
              break loop;
            default:
              Command commandHandler = commandMap.get(command);

              if (commandHandler == null) {
                System.out.println("실행할 수 없는 명령입니다.");
              } else {
                commandHandler.service();
                // 이제 명령어와 그 명령어를 처리하는 핸들러를 추가할 때 마다
                // case 문을 추가할 필요가 없다.
              }
          }
        } catch (Exception e) {
          System.out.println("------------------------------------------");
          System.out.printf("명령어 실행 중 오류 발생: %s - %s\n", 
              e.getClass().getName(), e.getMessage());
          System.out.println("------------------------------------------");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }

    Prompt.close();

    notifyOnServiceStopped();
  }

  private void notifyOnServiceStarted() {
    for (ApplicationContextListener listener : listeners) {
      // 옵저버의 메서드를 호출할 때 옵저버에게 전달하고 싶은 값이 있다면,
      // 맵 객체에 담아서 전달하면 된다.
      // 옵저버 또한 작업한 결과를 App에게 리턴해주고 싶다면,
      // 맵 객체에 담으면 된다.
      // 이를 위해 옵저버 메서드를 호출할 때 파라미터 값으로 맵 객체를 넘긴다.
      listener.contextInitialized(appContext);
    }
  }

  private void notifyOnServiceStopped() {
    for (ApplicationContextListener listener : listeners) {
      // 옵저버의 메서드를 호출할 때 옵저버에게 전달하고 싶은 값이 있다면,
      // 맵 객체에 담아서 전달하면 된다.
      // 옵저버 또한 작업한 결과를 App에게 리턴해주고 싶다면,
      // 맵 객체에 담으면 된다.
      // 이를 위해 옵저버 메서드를 호출할 때 파라미터 값으로 맵 객체를 넘긴다.
      listener.contextDestroyed(appContext);
    }
  }

  private void printCommandHistory(Iterator<String> iterator) {
    int count = 0;
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
      if ((++count % 5) == 0) {
        String input = Prompt.inputString(": ");
        if (input.equalsIgnoreCase("q")) {
          break;
        }
      }
    }
  }


}
