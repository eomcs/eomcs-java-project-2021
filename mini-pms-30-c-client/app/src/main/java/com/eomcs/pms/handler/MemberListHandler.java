package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberListHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[회원 목록]");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select no,name,email,photo,tel from pms_member order by name asc");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        System.out.printf("%d, %s, %s, %s, %s\n", 
            rs.getInt("no"), 
            rs.getString("name"), 
            rs.getString("email"),
            rs.getString("photo"),
            rs.getString("tel"));
      }
    }
  }
}






