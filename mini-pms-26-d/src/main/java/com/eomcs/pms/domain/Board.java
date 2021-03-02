package com.eomcs.pms.domain;

import java.sql.Date;
import com.eomcs.util.CsvObject;

public class Board implements CsvObject {
  private int no;
  private String title;
  private String content;
  private String writer;
  private Date registeredDate;
  private int viewCount;
  private int like;

  public Board() {}

  public Board(String csv) {
    String[] fields = csv.split(","); // 번호,제목,내용,작성자,등록일,조회수
    this.setNo(Integer.parseInt(fields[0]));
    this.setTitle(fields[1]);
    this.setContent(fields[2]);
    this.setWriter(fields[3]);
    this.setRegisteredDate(Date.valueOf(fields[4]));
    this.setViewCount(Integer.parseInt(fields[5]));
  }

  @Override
  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%d", 
        this.getNo(),
        this.getTitle(),
        this.getContent(),
        this.getWriter(),
        this.getRegisteredDate().toString(),
        this.getViewCount());
  }

  // 다음과 같이 인스턴스를 생성해주는 메서드를 
  // "factory method"라 부른다.
  // 팩토리 메서드 패턴
  // - 인스턴스 생성 과정이 복잡할 때 
  //   인스턴스 생성을 대신 해주는 메서드를 만들어
  //   그 메서드를 통해 객체를 생성하는 프로그래밍 방식이다.
  public static Board valueOfCsv(String csv) {
    String[] fields = csv.split(","); // 번호,제목,내용,작성자,등록일,조회수
    Board b = new Board();
    b.setNo(Integer.parseInt(fields[0]));
    b.setTitle(fields[1]);
    b.setContent(fields[2]);
    b.setWriter(fields[3]);
    b.setRegisteredDate(Date.valueOf(fields[4]));
    b.setViewCount(Integer.parseInt(fields[5]));
    return b;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + no;
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((writer == null) ? 0 : writer.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Board other = (Board) obj;
    if (no != other.no)
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    if (writer == null) {
      if (other.writer != null)
        return false;
    } else if (!writer.equals(other.writer))
      return false;
    return true;
  }

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getWriter() {
    return writer;
  }
  public void setWriter(String writer) {
    this.writer = writer;
  }
  public Date getRegisteredDate() {
    return registeredDate;
  }
  public void setRegisteredDate(Date registeredDate) {
    this.registeredDate = registeredDate;
  }
  public int getViewCount() {
    return viewCount;
  }
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }
  public int getLike() {
    return like;
  }
  public void setLike(int like) {
    this.like = like;
  }


}
