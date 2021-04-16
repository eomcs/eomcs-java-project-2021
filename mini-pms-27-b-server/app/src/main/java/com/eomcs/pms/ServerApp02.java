package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import com.eomcs.util.concurrent.ThreadPool;

public class ServerApp02 {

  int port;

  // 서버의 상태를 설정
  boolean isStop;

  public static void main(String[] args) {
    ServerApp02 app = new ServerApp02(8888);
    app.service();
  }

  public ServerApp02(int port) {
    this.port = port;
  }

  public void service() {

    // 스레드풀 준비
    ThreadPool threadPool = new ThreadPool();

    // 클라이언트 연결을 기다리는 서버 소켓 생성
    try (ServerSocket serverSocket = new ServerSocket(this.port)) {

      System.out.println("서버 실행!");

      while (true) {
        Socket socket = serverSocket.accept();
        if (isStop) { // 서버의 상태가 종료이면,
          break; // 즉시 반복문을 탈출하여 main 스레드의 실행을 끝낸다.
        }

        threadPool.execute(() -> processRequest(socket));
      }

    } catch (Exception e) {
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }
    System.out.println("서버 종료!");
  }

  public void processRequest(Socket socket) {
    try (
        Socket clientSocket = socket;
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
        ) {

      while (true) {
        // 클라이언트가 보낸 요청을 읽는다.
        String requestLine = in.readLine();

        if (requestLine.equalsIgnoreCase("serverstop")) {
          in.readLine(); // 요청의 끝을 의미하는 빈 줄을 읽는다.
          out.println("Server stopped!");
          out.println();
          out.flush();
          terminate();
          return; 
        }

        if (requestLine.equalsIgnoreCase("exit") || requestLine.equalsIgnoreCase("quit")) {
          in.readLine(); // 요청의 끝을 의미하는 빈 줄을 읽는다.
          out.println("Goodbye!");
          out.println();
          out.flush();
          return;
        }

        // 클라이언트 보낸 명령을 서버 창에 출력한다.
        System.out.println(requestLine);

        // 클라이언트가 보낸 데이터를 읽는다.
        while (true) {
          String line = in.readLine();
          if (line.length() == 0) {
            break;
          }
          // 클라이언트에서 보낸 데이터를 서버 창에 출력해 보자.
          System.out.println(line);
        }
        System.out.println("------------------------------------");

        // 클라이언트에게 응답한다.
        out.println("OK");
        out.printf("====> %s\n", requestLine);   
        out.println();
        out.flush();
      }

    } catch (Exception e) {
      System.out.println("클라이언트의 요청을 처리하는 중에 오류 발생!");
      e.printStackTrace();
    }
  }

  // 서버를 최종적으로 종료하는 일을 한다.
  private void terminate() {
    // 서버 상태를 종료로 설정한다.
    isStop = true;

    // 그리고 서버가 즉시 종료할 수 있도록 임의의 접속을 수행한다.
    // => 스스로 클라이언트가 되어 ServerSocket 에 접속하면 
    //    accept()에서 리턴하기 때문에 isStop 변수의 상태에 따라 반복문을 멈출 것이다.
    try (Socket socket = new Socket("localhost", 8888)) {
      // 서버를 종료시키기 위해 임의로 접속하는 것이기 때문에 특별히 추가로 해야 할 일이 없다.
    } catch (Exception e) {}
  }

}
