package com.eomcs.pms;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// 데이터를 파일에 보관하고 꺼내는 일을 할 애플리케이션
public class ServerApp {
  public static void main(String[] args) {

    // 클라이언트 연결을 기다는 서버 소켓 생성
    try (ServerSocket serverSocket = new ServerSocket(8888)) {

      // 클라이언트와 연결 수행
      try (Socket socket = serverSocket.accept();
          OutputStream out = socket.getOutputStream();
          InputStream in = socket.getInputStream()) {

      } catch (Exception e) {
        System.out.println("클라이언트 요청 처리 중 오류 발생!");
        e.printStackTrace();
      }

    } catch (Exception e) {
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
