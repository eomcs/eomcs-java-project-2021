package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.eomcs.util.Prompt;

public class ClientApp {

  String sessionId;

  public static void main(String[] args) {
    ClientApp app = new ClientApp();
    try {
      app.execute();
    } catch (Exception e) {
      System.out.println("클라이언트 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

  public void execute() throws Exception {
    // Stateless 통신 방식
    while (true) {
      String input = com.eomcs.util.Prompt.inputString("명령> ");
      if (input.length() == 0) {
        continue;
      }

      if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit")) {
        break;
      }

      requestService(input);

      if (input.equalsIgnoreCase("serverstop")) {
        break;
      }

      System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
    }

    System.out.println("안녕!");
    Prompt.close();
  }

  private void requestService(String input) {
    // input:
    //   - 예1) 192.168.0.2:8888/board/list
    //   - 예2) 192.168.0.2/board/list

    // => 사용자가 입력한 문자열에서 서버에 요구하는 명령을 추출한다.
    int i = input.indexOf('/');
    String command = input.substring(i); // => /board/list

    // => 사용자가 입력한 문자열에서 서버 주소와 포트 번호를 분리하여 추출한다.
    String[] values = input.substring(0, i).split(":"); // => {"192.168.0.2", "8888"} 
    String serverAddress = values[0]; // => "192.168.0.2"
    int port = 8888;
    if (values.length > 1) {
      port = Integer.parseInt(values[1]);
    }

    try (Socket socket = new Socket(serverAddress, port);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {

      // 서버에게 명령을 보낸다.
      out.println(command);
      if (sessionId != null) {
        out.printf("SESSION_ID:%s\n", sessionId);
      }
      out.println();
      out.flush();

      // 서버가 보내온 응답 헤더를 읽는다.
      // => 상태 값을 읽는다.
      in.readLine(); // 상태 값은 당장 사용하지는 않는다.

      // => 응답 헤더를 읽는다.
      while (true) {
        String line = in.readLine();
        if (line.length() == 0) {
          break;
        }

        // 서버가 세션 아이디를 보내 주면 보관해 뒀다가,
        // 서버에 요청할 때마다 요청 헤더로 다시 보내 준다.
        // 왜?
        // 클라이언트를 구분할 수 있도록 하기 위함이다.
        if (line.startsWith("SESSION_ID:")) {
          sessionId = line.substring(11);
        }
      }

      // 서버가 응답한 데이터를 출력한다.
      String line = null;
      while (true) {
        line = in.readLine();

        if (line.length() == 0) {
          break;
        } else if (line.equals("!{}!")) {
          String value = Prompt.inputString("입력> ");
          out.println(value);
          out.flush();
        } else {
          System.out.println(line);
        }
      }
    } catch (Exception e) {
      System.out.println("통신 오류 발생!");
    }
  }
}
