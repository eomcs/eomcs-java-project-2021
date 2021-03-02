package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Date;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
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

public class App {

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  static ArrayDeque<String> commandStack = new ArrayDeque<>();
  static LinkedList<String> commandQueue = new LinkedList<>();

  // VO 를 저장할 컬렉션 객체
  static ArrayList<Board> boardList = new ArrayList<>();
  static ArrayList<Member> memberList = new ArrayList<>();
  static LinkedList<Project> projectList = new LinkedList<>();
  static LinkedList<Task> taskList = new LinkedList<>();

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
    saveBoards();
    saveMembers();
    saveProjects();
    saveTasks();

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

  static void loadBoards() {
    try (BufferedReader in = new BufferedReader(new FileReader("boards.csv"))) {
      String record = null;
      while ((record = in.readLine()) != null) {
        String[] fields = record.split(","); // 번호,제목,내용,작성자,등록일,조회수
        Board b = new Board();
        b.setNo(Integer.parseInt(fields[0]));
        b.setTitle(fields[1]);
        b.setContent(fields[2]);
        b.setWriter(fields[3]);
        b.setRegisteredDate(Date.valueOf(fields[4]));
        b.setViewCount(Integer.parseInt(fields[5]));

        boardList.add(b);
      }
      System.out.println("게시글 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("게시글 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveBoards() {
    try (BufferedWriter out = new BufferedWriter(new FileWriter("boards.csv"))) {
      // boards.csv 파일 포맷
      // - 번호,제목,내용,작성자,등록일,조회수(CRLF)
      for (Board b : boardList) {
        out.write(String.format("%d,%s,%s,%s,%s,%d\n", 
            b.getNo(),
            b.getTitle(),
            b.getContent(),
            b.getWriter(),
            b.getRegisteredDate().toString(),
            b.getViewCount()));
      }
      System.out.println("게시글 데이터 저장!");

    } catch (Exception e) {
      System.out.println("게시글 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadMembers() {
    try (BufferedReader in = new BufferedReader(new FileReader("members.csv"))) {
      String record = null;
      while ((record = in.readLine()) != null) {
        String[] fields = record.split(",");
        Member member = new Member();
        member.setNo(Integer.parseInt(fields[0]));
        member.setName(fields[1]);
        member.setEmail(fields[2]);
        member.setPassword(fields[3]);
        member.setPhoto(fields[4]);
        member.setTel(fields[5]);
        member.setRegisteredDate(Date.valueOf(fields[6]));
        memberList.add(member);
      }
      System.out.println("회원 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("회원 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveMembers() {
    try (BufferedWriter out = new BufferedWriter(new FileWriter("members.csv"))) {
      for (Member member : memberList) {
        // 회원 목록에서 회원 데이터를 꺼내 CSV 형식으로 출력한다.
        out.write(String.format("%d,%s,%s,%s,%s,%s,%s\n", 
            member.getNo(),
            member.getName(),
            member.getEmail(),
            member.getPassword(),
            member.getPhoto(),
            member.getTel(),
            member.getRegisteredDate()));
      }
      System.out.println("회원 데이터 저장!");

    } catch (Exception e) {
      System.out.println("회원 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadProjects() {
    try (BufferedReader in = new BufferedReader(new FileReader("projects.csv"))) {
      String record = null;
      while ((record = in.readLine()) != null) {
        String[] fields = record.split(",");
        Project project = new Project();
        project.setNo(Integer.parseInt(fields[0]));
        project.setTitle(fields[1]);
        project.setContent(fields[2]);
        project.setStartDate(Date.valueOf(fields[3]));
        project.setEndDate(Date.valueOf(fields[4]));
        project.setOwner(fields[5]);
        project.setMembers(fields[6].replace("|", ","));
        projectList.add(project);
      }
      System.out.println("프로젝트 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("프로젝트 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveProjects() {
    try (BufferedWriter out = new BufferedWriter(new FileWriter("projects.csv"))) {
      for (Project project : projectList) {
        // 프로젝트 목록에서 프로젝트 데이터를 꺼내 CSV 형식으로 출력한다.
        out.write(String.format("%d,%s,%s,%s,%s,%s,%s\n", 
            project.getNo(),
            project.getTitle(),
            project.getContent(),
            project.getStartDate(),
            project.getEndDate(),
            project.getOwner(),
            project.getMembers().replace(",", "|")));
      }
      System.out.println("프로젝트 데이터 저장!");

    } catch (Exception e) {
      System.out.println("프로젝트 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadTasks() {
    try (BufferedReader in = new BufferedReader(new FileReader("tasks.csv"))) {
      String record = null;
      while ((record = in.readLine()) != null) {
        String[] data = record.split(",");
        Task task = new Task();
        task.setNo(Integer.parseInt(data[0]));
        task.setContent(data[1]);
        task.setDeadline(Date.valueOf(data[2]));
        task.setStatus(Integer.parseInt(data[3]));
        task.setOwner(data[4]);
        taskList.add(task);
      }
      System.out.println("작업 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("작업 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveTasks() {
    try (BufferedWriter out = new BufferedWriter(new FileWriter("tasks.csv"))) {
      for (Task task : taskList) {
        // 작업 목록에서 작업 데이터를 꺼내 CSV 형식으로 출력한다.
        out.write(String.format("%d,%s,%s,%d,%s\n", 
            task.getNo(),
            task.getContent(),
            task.getDeadline(),
            task.getStatus(),
            task.getOwner()));
      }
      System.out.println("작업 데이터 저장!");

    } catch (Exception e) {
      System.out.println("작업 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }
}
