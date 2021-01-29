package com.eomcs.pms;

import com.eomcs.pms.handler.BoardHandler;
import com.eomcs.pms.handler.MemberHandler;
import com.eomcs.pms.handler.ProjectHandler;
import com.eomcs.pms.handler.TaskHandler;
import com.eomcs.util.Prompt;

public class App {

  public static void main(String[] args) {

    BoardHandler boardList = new BoardHandler();
    MemberHandler memberList = new MemberHandler();
    ProjectHandler projectList = new ProjectHandler(memberList);
    TaskHandler taskList = new TaskHandler(memberList);


    loop:
      while (true) {
        String command = com.eomcs.util.Prompt.inputString("명령> ");

        switch (command) {
          case "/member/add":
            memberList.add();
            break;
          case "/member/list":
            memberList.list();
            break;
          case "/member/detail":
            memberList.detail();
            break;  
          case "/member/update":
            memberList.update();
            break; 
          case "/member/delete":
            memberList.delete();
            break;
          case "/project/add":
            projectList.add();
            break;
          case "/project/list":
            projectList.list();
            break;
          case "/project/detail": 
            projectList.detail();
            break;  
          case "/project/update":
            projectList.update();
            break; 
          case "/project/delete":
            projectList.delete();
            break;
          case "/task/add":
            taskList.add();
            break;
          case "/task/list":
            taskList.list();
            break;
          case "/task/detail": 
            taskList.detail();
            break;  
          case "/task/update":
            taskList.update();
            break; 
          case "/task/delete":
            taskList.delete();
            break;
          case "/board/add":
            boardList.add();
            break;
          case "/board/list":
            boardList.list();
            break;
          case "/board/detail":
            boardList.detail();
            break;  
          case "/board/update":
            boardList.update();
            break; 
          case "/board/delete":
            boardList.delete();
            break;
          case "quit":
          case "exit":
            System.out.println("안녕!");
            break loop;
          default:
            System.out.println("실행할 수 없는 명령입니다.");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }

    Prompt.close();
  }
}
