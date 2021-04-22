package com.eomcs.util;

// 클라이언트의 요청 정보를 다루는 역할
public class CommandRequest {

  private String commandPath;
  private String remoteAddr;
  private int remotePort;
  private Prompt prompt;
  private Session session;

  public CommandRequest(
      String commandPath, 
      String remoteAddr, 
      int remotePort, 
      Prompt prompt, 
      Session session) {

    this.commandPath = commandPath;
    this.remoteAddr = remoteAddr;
    this.remotePort = remotePort;
    this.prompt = prompt;
    this.session = session;
  }

  public String getCommandPath() {
    return commandPath;
  }

  public String getRemoteAddr() {
    return remoteAddr;
  }

  public int getRemotePort() {
    return remotePort;
  }

  public Prompt getPrompt() {
    return prompt;
  }

  public Session getSession() {
    return session;
  }
}
