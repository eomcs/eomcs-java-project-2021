package com.eomcs.pms.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.CsvObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FileListener implements ApplicationContextListener {

  // 데이터 파일 정보
  File boardFile = new File("boards.json");
  File memberFile = new File("members.json");
  File projectFile = new File("projects.json");
  File taskFile = new File("tasks.json");

  // VO 를 저장할 컬렉션 객체
  public ArrayList<Board> boardList = new ArrayList<>();
  ArrayList<Member> memberList = new ArrayList<>();
  LinkedList<Project> projectList = new LinkedList<>();
  LinkedList<Task> taskList = new LinkedList<>();


  @Override
  public void contextInitialized() {
    // 파일에서 데이터를 읽어온다.(데이터 로딩)
    loadObjects(boardFile, boardList, Board.class);
    loadObjects(memberFile, memberList, Member.class);
    loadObjects(projectFile, projectList, Project.class);
    loadObjects(taskFile, taskList, Task.class);
  }

  @Override
  public void contextDestroyed() {
    // 데이터를 파일로 출력한다.
    saveObjects(boardFile, boardList);
    saveObjects(memberFile, memberList);
    saveObjects(projectFile, projectList);
    saveObjects(taskFile, taskList);
  }

  private <T> void loadObjects(File file, List<T> list, Class<T> elementType) {

    try (BufferedReader in = new BufferedReader(new FileReader(file))) {

      StringBuilder strBuilder = new StringBuilder();
      String str = null;
      while ((str = in.readLine()) != null) {
        strBuilder.append(str);
      }

      Type collectionType = TypeToken.getParameterized(Collection.class, elementType).getType();
      Collection<T> collection = new Gson().fromJson(strBuilder.toString(), collectionType);

      list.addAll(collection);

      System.out.printf("%s 파일 데이터 로딩!\n", file.getName());

    } catch (Exception e) {
      System.out.printf("%s 파일 데이터 로딩 중 오류 발생!\n", file.getName());
    }
  }

  private <T extends CsvObject> void saveObjects(File file, List<T> list) {
    try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
      out.write(new Gson().toJson(list));
      System.out.printf("파일 %s 데이터 저장!\n", file.getName());

    } catch (Exception e) {
      System.out.printf("파일 %s에 데이터를 저장하는 중에 오류 발생!\n", file.getName());
    }
  }

}
