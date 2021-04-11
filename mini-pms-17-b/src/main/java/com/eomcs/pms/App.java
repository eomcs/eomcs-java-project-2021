package com.eomcs.pms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
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
import com.eomcs.util.Prompt;

public class App {

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  static ArrayDeque<String> commandStack = new ArrayDeque<>();
  static LinkedList<String> commandQueue = new LinkedList<>();


  public static void main(String[] args) throws CloneNotSupportedException {

    ArrayList<Board> boardList = new ArrayList<>();
    BoardAddHandler boardAddHandler = new BoardAddHandler(boardList);
    BoardListHandler boardListHandler = new BoardListHandler(boardList);
    BoardDetailHandler boardDetailHandler = new BoardDetailHandler(boardList);
    BoardUpdateHandler boardUpdateHandler = new BoardUpdateHandler(boardList);
    BoardDeleteHandler boardDeleteHandler = new BoardDeleteHandler(boardList);

    ArrayList<Member> memberList = new ArrayList<>();
    MemberAddHandler memberAddHandler = new MemberAddHandler(memberList);
    MemberListHandler memberListHandler = new MemberListHandler(memberList);
    MemberDetailHandler memberDetailHandler = new MemberDetailHandler(memberList);
    MemberUpdateHandler memberUpdateHandler = new MemberUpdateHandler(memberList);
    MemberDeleteHandler memberDeleteHandler = new MemberDeleteHandler(memberList);
    MemberValidatorHandler memberValidatorHandler = new MemberValidatorHandler(memberList);

    LinkedList<Project> projectList = new LinkedList<>();
    ProjectAddHandler projectAddHandler = new ProjectAddHandler(projectList, memberValidatorHandler);
    ProjectListHandler projecListHandler = new ProjectListHandler(projectList);
    ProjectDetailHandler projectDetailHandler = new ProjectDetailHandler(projectList);
    ProjectUpdateHandler projectUpdateHandler = new ProjectUpdateHandler(projectList, memberValidatorHandler);
    ProjectDeleteHandler projectDeleteHandler = new ProjectDeleteHandler(projectList);

    LinkedList<Task> taskList = new LinkedList<>();
    TaskAddHandler taskAddHandler = new TaskAddHandler(taskList, memberValidatorHandler);
    TaskListHandler taskListHandler = new TaskListHandler(taskList);
    TaskDetailHandler taskDetailHandler = new TaskDetailHandler(taskList);
    TaskUpdateHandler taskUpdateHandler = new TaskUpdateHandler(taskList, memberValidatorHandler);
    TaskDeleteHandler taskDeleteHandler = new TaskDeleteHandler(taskList);

    // 새 기능 추가
    BoardSearchHandler boardSearchHandler = new BoardSearchHandler(boardList);
    HelloHandler helloHandler = new HelloHandler();

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
            case "/member/add":
              memberAddHandler.add();
              break;
            case "/member/list":
              memberListHandler.list();
              break;
            case "/member/detail":
              memberDetailHandler.detail();
              break;  
            case "/member/update":
              memberUpdateHandler.update();
              break; 
            case "/member/delete":
              memberDeleteHandler.delete();
              break;
            case "/project/add":
              projectAddHandler.add();
              break;
            case "/project/list":
              projecListHandler.list();
              break;
            case "/project/detail": 
              projectDetailHandler.detail();
              break;  
            case "/project/update":
              projectUpdateHandler.update();
              break; 
            case "/project/delete":
              projectDeleteHandler.delete();
              break;
            case "/task/add":
              taskAddHandler.add();
              break;
            case "/task/list":
              taskListHandler.list();
              break;
            case "/task/detail": 
              taskDetailHandler.detail();
              break;  
            case "/task/update":
              taskUpdateHandler.update();
              break; 
            case "/task/delete":
              taskDeleteHandler.delete();
              break;
            case "/board/add":
              boardAddHandler.add();
              break;
            case "/board/list":
              boardListHandler.list();
              break;
            case "/board/detail":
              boardDetailHandler.detail();
              break;  
            case "/board/update":
              boardUpdateHandler.update();
              break; 
            case "/board/delete":
              boardDeleteHandler.delete();
              break;
            case "/board/search":
              boardSearchHandler.search();
              break;
            case "/hello":
              helloHandler.hello();
              break;
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
              System.out.println("실행할 수 없는 명령입니다.");
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
  }

  static void printCommandHistory(Iterator<String> iterator) {
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
