package com.eomcs.pms.handler;

import java.util.Iterator;
import com.eomcs.driver.Statement;
import com.eomcs.util.Prompt;

public class BoardSearchHandler implements Command {

  @Override
  public void service(Statement stmt) throws Exception {
    String keyword = Prompt.inputString("검색어? ");

    if (keyword.length() == 0) {
      System.out.println("검색어를 입력하세요.");
      return;
    }

    Iterator<String> results = stmt.executeQuery("board/selectByKeyword", keyword);

    if (!results.hasNext()) {
      System.out.println("검색어에 해당하는 게시글이 없습니다.");
      return;
    }

    while (results.hasNext()) {
      String[] fields = results.next().split(",");
      System.out.printf("%s, %s, %s, %s, %s\n", 
          fields[0], 
          fields[1], 
          fields[2],
          fields[3],
          fields[4]);
    }
  }
}






