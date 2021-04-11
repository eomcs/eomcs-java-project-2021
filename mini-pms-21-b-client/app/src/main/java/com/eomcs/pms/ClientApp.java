package com.eomcs.pms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientApp {

  String serverAddress;
  int port;

  public static void main(String[] args) {
    ClientApp app = new ClientApp("localhost", 8888);
    app.execute();
  }

  public ClientApp(String serverAddress, int port) {
    this.serverAddress = serverAddress;
    this.port = port;
  }

  public void execute() {

    try (Socket socket = new Socket(this.serverAddress, this.port);
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
