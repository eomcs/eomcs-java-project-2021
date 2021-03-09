package com.eomcs.pms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// 데이터를 파일에 보관하고 꺼내는 일을 할 애플리케이션
public class ServerApp {
  public static void main(String[] args) {

    // 클라이언트 연결을 기다는 서버 소켓 생성
    try (ServerSocket serverSocket = new ServerSocket(8888)) {

      System.out.println("서버 실행!");

      // 클라이언트와 연결 수행
      try (Socket socket = serverSocket.accept();
          DataOutputStream out = new DataOutputStream(socket.getOutputStream());
          DataInputStream in = new DataInputStream(socket.getInputStream())) {

        String request = in.readUTF();
        System.out.println(request);

        out.writeUTF("success");
        out.flush();

      } catch (Exception e) {
        System.out.println("클라이언트의 요청을 처리하는 중에 오류 발생!");
        e.printStackTrace();
      }

    } catch (Exception e) {
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
