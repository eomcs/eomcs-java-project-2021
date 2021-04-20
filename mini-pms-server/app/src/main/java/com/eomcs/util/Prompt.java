package com.eomcs.util;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Date;

public class Prompt {

  private BufferedReader in;
  private PrintWriter out;

  public Prompt(BufferedReader in, PrintWriter out) {
    this.in = in;
    this.out = out;
  }

  public String inputString(String title) throws Exception {
    out.println(title);
    out.println("!{}!");
    out.flush();
    return in.readLine();
  }

  public int inputInt(String title) throws Exception {
    return Integer.parseInt(inputString(title));
  }

  public Date inputDate(String title) throws Exception {
    return Date.valueOf(inputString(title));
  }
}
