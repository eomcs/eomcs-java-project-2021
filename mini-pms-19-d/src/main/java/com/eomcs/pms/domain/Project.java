package com.eomcs.pms.domain;

import java.sql.Date;
import com.eomcs.util.CsvObject;

public class Project implements CsvObject {
  private int no;
  private String title;
  private String content;
  private Date startDate;
  private Date endDate;
  private String owner;
  private String members;

  public Project() {}

  public Project(String csv) {
    String[] fields = csv.split(",");
    this.setNo(Integer.parseInt(fields[0]));
    this.setTitle(fields[1]);
    this.setContent(fields[2]);
    this.setStartDate(Date.valueOf(fields[3]));
    this.setEndDate(Date.valueOf(fields[4]));
    this.setOwner(fields[5]);
    this.setMembers(fields[6].replace("|", ","));
  }

  @Override
  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%s,%s", 
        this.getNo(),
        this.getTitle(),
        this.getContent(),
        this.getStartDate(),
        this.getEndDate(),
        this.getOwner(),
        this.getMembers().replace(",", "|"));
  }

  public static Project valueOfCsv(String csv) {
    String[] fields = csv.split(",");
    Project project = new Project();
    project.setNo(Integer.parseInt(fields[0]));
    project.setTitle(fields[1]);
    project.setContent(fields[2]);
    project.setStartDate(Date.valueOf(fields[3]));
    project.setEndDate(Date.valueOf(fields[4]));
    project.setOwner(fields[5]);
    project.setMembers(fields[6].replace("|", ","));
    return project;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
    result = prime * result + no;
    result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
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
    Project other = (Project) obj;
    if (endDate == null) {
      if (other.endDate != null)
        return false;
    } else if (!endDate.equals(other.endDate))
      return false;
    if (no != other.no)
      return false;
    if (startDate == null) {
      if (other.startDate != null)
        return false;
    } else if (!startDate.equals(other.startDate))
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
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
  public Date getStartDate() {
    return startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  public String getOwner() {
    return owner;
  }
  public void setOwner(String owner) {
    this.owner = owner;
  }
  public String getMembers() {
    return members;
  }
  public void setMembers(String members) {
    this.members = members;
  }  


}
