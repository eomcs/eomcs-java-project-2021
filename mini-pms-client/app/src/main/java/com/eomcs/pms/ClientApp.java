package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.eomcs.util.Prompt;

public class ClientApp {

  String serverAddress;
  int port;

  public static void main(String[] args) {

    String serverAddress = Prompt.inputString("서버 주소? ");
    int port = Prompt.inputInt("서버 포트? ");

    ClientApp app = new ClientApp(serverAddress, port);

    try {
      app.execute();

    } catch (Exception e) {
      System.out.println("클라이언트 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

  public ClientApp(String serverAddress, int port) {
    this.serverAddress = serverAddress;
    this.port = port;
  }

  public void execute() throws Exception {
    // Stateful 통신 방식
    try (
        // 1) 서버와 연결하기
        Socket socket = new Socket(serverAddress, port);

        // 2) 데이터 입출력 스트림 객체를 준비
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {

      while (true) {
        String command = com.eomcs.util.Prompt.inputString("명령> ");
        if (command.length() == 0) {
          continue;
        }

        // 서버에 명령을 보낸 후 그 결과를 받아 출력한다.
        out.println(command);
        out.println();
        out.flush();

        String line = null;
        while (true) {
          line = in.readLine();
          if (line.length() == 0) {
            break;
          }
          System.out.println(line);
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력

        if (command.equalsIgnoreCase("quit") || 
            command.equalsIgnoreCase("exit") ||
            command.equalsIgnoreCase("serverstop")) {
          System.out.println("안녕!");
          break;
        }
      }

    } catch (Exception e) {
      System.out.println("통신 오류 발생!");
    }

    Prompt.close();
  }
}
