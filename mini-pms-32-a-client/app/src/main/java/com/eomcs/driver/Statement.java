package com.eomcs.driver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Statement implements AutoCloseable {

  Socket socket;
  DataInputStream in;
  DataOutputStream out;

  public Statement(String host, int port) throws Exception {
    socket = new Socket(host, port);
    in = new DataInputStream(socket.getInputStream());
    out = new DataOutputStream(socket.getOutputStream());
  }

  // 데이터를 입력, 변경, 삭제할 때 호출하는 메서드 
  public void executeUpdate(String command, String... args) throws Exception {
    request(command, args);

    // 서버의 응답 결과를 받는다.
    String status = in.readUTF();
    in.readInt();
    if (status.equals("error")) {
      throw new Exception(in.readUTF());
    }
  }

  // 데이터 목록을 조회하거나 특정 항목을 조회할 때 호출하는 메서드
  public Iterator<String> executeQuery(String command, String... args) throws Exception {
    request(command, args);

    // 서버의 응답 결과를 받는다.
    String status = in.readUTF();
    int length = in.readInt();

    if (status.equals("error")) {
      throw new Exception(in.readUTF());
    }

    // 응답 결과를 담을 컬렉션 준비
    ArrayList<String> results = new ArrayList<>();

    for (int i = 0; i < length; i++) {
      results.add(in.readUTF());
    }

    return results.iterator();
  }

  private void request(String command, String... args) throws Exception {
    // 서버에 요청을 보낸다.
    out.writeUTF(command);
    out.writeInt(args.length);
    for (String data : args) {
      out.writeUTF(data);
    }
    out.flush();
  }

  @Override
  public void close() {
    try {in.close();} catch (Exception e) {}
    try {out.close();} catch (Exception e) {}
    try {socket.close();} catch (Exception e) {}
  }
}







