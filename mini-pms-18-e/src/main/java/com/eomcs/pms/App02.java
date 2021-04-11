package com.eomcs.pms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
import com.eomcs.util.Prompt;

// 1) 파일 명을 파일 객체로 변경하고 스태틱 필드로 만들어 공유한다.
// 2) List 저장 메서드를 saveObjects() 메서드로 통합한다.
public class App02 {

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  static ArrayDeque<String> commandStack = new ArrayDeque<>();
  static LinkedList<String> commandQueue = new LinkedList<>();

  // VO 를 저장할 컬렉션 객체
  static List<Board> boardList;
  static List<Member> memberList;
  static List<Project> projectList;
  static List<Task> taskList;

  // 데이터 파일 정보
  static File boardFile = new File("boards.data");
  static File memberFile = new File("members.data");
  static File projectFile = new File("projects.data");
  static File taskFile = new File("tasks.data");

  public static void main(String[] args) {


    // 파일에서 데이터를 읽어온다.(데이터 로딩)
    loadBoards();
    loadMembers();
    loadProjects();
    loadTasks();

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

    // 게시글 데이터를 파일로 출력한다.
    saveObjects(boardFile, boardList);
    saveObjects(memberFile, memberList);
    saveObjects(projectFile, projectList);
    saveObjects(taskFile, taskList);

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

  @SuppressWarnings("unchecked")
  static void loadBoards() {
    try (ObjectInputStream in = new ObjectInputStream(
        new BufferedInputStream(
            new FileInputStream(boardFile)))) {

      boardList = (List<Board>) in.readObject();
      System.out.println("게시글 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("게시글 데이터 로딩 중 오류 발생!");
      boardList = new ArrayList<>();
    }
  }

  static void saveObjects(File file, List<?> dataList) {
    try (ObjectOutputStream out = new ObjectOutputStream(
        new BufferedOutputStream(
            new FileOutputStream(file)))) {

      out.writeObject(dataList);
      System.out.printf("파일 %s 저장!\n", file.getName());

    } catch (Exception e) {
      System.out.printf("파일 %s 저장 중 오류 발생!\n", file.getName());
    }
  }

  @SuppressWarnings("unchecked")
  static void loadMembers() {

    try (ObjectInputStream in = new ObjectInputStream(
        new BufferedInputStream(
            new FileInputStream(memberFile)))) {

      memberList = (List<Member>) in.readObject();
      System.out.println("회원 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("회원 데이터 로딩 중 오류 발생!");
      memberList = new ArrayList<>();
    }
  }

  @SuppressWarnings("unchecked")
  static void loadProjects() {

    try (ObjectInputStream in = new ObjectInputStream(
        new BufferedInputStream(
            new FileInputStream(projectFile)))) {

      projectList = (List<Project>) in.readObject();
      System.out.println("프로젝트 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("프로젝트 데이터 로딩 중 오류 발생!");
      projectList = new LinkedList<>();
    }
  }

  @SuppressWarnings("unchecked")
  static void loadTasks() {

    try (ObjectInputStream in = new ObjectInputStream(
        new BufferedInputStream(
            new FileInputStream(taskFile)))) {

      taskList = (List<Task>) in.readObject();
      System.out.println("작업 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("작업 데이터 로딩 중 오류 발생!");
      taskList = new LinkedList<>();
    }
  }
}
