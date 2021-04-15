package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import com.eomcs.pms.table.DataTable;
import com.oracle.truffle.api.impl.TruffleLocator.Response;

public class ServerApp {

  int port;

  public static void main(String[] args) {
    ServerApp app = new ServerApp(8888);
    app.service();
  }

  public ServerApp(int port) {
    this.port = port;
  }

  public void service() {
    // 클라이언트 연결을 기다리는 서버 소켓 생성
    try (ServerSocket serverSocket = new ServerSocket(this.port)) {

      System.out.println("서버 실행!");

      while (true) {
        Socket socket = serverSocket.accept();
        new Thread(() -> processRequest(socket)).start();
      }

    } catch (Exception e) {
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

  public void processRequest(Socket socket) {
    try (
        Socket clientSocket = socket;
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
        ) {

      loop: while (true) {
        String line = null;
        while (true) {
          line = in.readLine();

          // 클라이언트에서 보낸 것을 서버 창에 출력해 보자.
          System.out.println(line);

          if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("quit")) {
            in.readLine();
            out.println("Goodbye!");
            out.println();
            out.flush();
            break loop;
          }
        }

        if (request.getCommand().equals("quit")) {
          sendResponse(out, "success");
          break;
        }

        DataTable dataTable = findDataTable(request.getCommand());

        if (dataTable != null) {
          Response response = new Response();
          try {
            dataTable.service(request, response);          
            sendResponse(
                out, 
                "success", 
                response.getDataList().toArray(new String[response.getDataList().size()]));

          } catch (Exception e) {
            sendResponse(
                out, 
                "error", 
                e.getMessage() != null ? e.getMessage() : e.getClass().getName());
          }

        } else {
          sendResponse(out, "error", "해당 요청을 처리할 수 없습니다!");
        }
      }

    } catch (Exception e) {
      System.out.println("클라이언트의 요청을 처리하는 중에 오류 발생!");
      e.printStackTrace();
    }
  }

}
