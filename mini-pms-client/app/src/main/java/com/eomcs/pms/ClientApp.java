package com.eomcs.pms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientApp {
  public static void main(String[] args) {

    // 서버와 연결한다.
    try (Socket socket = new Socket("localhost", 8888);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream())) {

      out.writeUTF("hello");
      out.flush();

      String response = in.readUTF();
      System.out.println(response);

    } catch (Exception e) {
      System.out.println("서버와 통신 하는 중에 오류 발생!");
    }

  }
}
