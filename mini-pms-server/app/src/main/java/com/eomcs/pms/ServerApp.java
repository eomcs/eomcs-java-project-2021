package com.eomcs.pms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import com.eomcs.pms.table.BoardTable;
import com.eomcs.pms.table.DataTable;

// 데이터를 파일에 보관하고 꺼내는 일을 할 애플리케이션
public class ServerApp {

  int port;
  HashMap<String,DataTable> tableMap = new HashMap<>();

  public static void main(String[] args) {
    ServerApp app = new ServerApp(8888);
    app.service();
  }

  public ServerApp(int port) {
    this.port = port;
  }

  public void service() {

    // 요청을 처리할 테이블 객체를 준비한다.
    tableMap.put("board/", new BoardTable());

    // 클라이언트 연결을 기다는 서버 소켓 생성
    try (ServerSocket serverSocket = new ServerSocket(this.port)) {

      System.out.println("서버 실행!");

      processRequest(serverSocket.accept());

    } catch (Exception e) {
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

  private void processRequest(Socket socket) {
    try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream())) {

      while (true) {
        // 1) 명령어 문자열을 읽는다.
        String request = in.readUTF();

        // 2) 클라이언트가 보낸 데이터의 개수를 읽는다.
        int length = in.readInt();

        // 3) 클라이언트가 보낸 데이터를 읽어서 List 컬렉션에 담는다.
        ArrayList<String> data = null;
        if (length > 0) {
          data = new ArrayList<>();
          for (int i = 0; i < length; i++) {
            data.add(in.readUTF());
          }
        }


        System.out.println("-------------------------------");
        System.out.printf("명령: %s\n", request);
        System.out.printf("데이터 개수: %d\n", length);
        if (data != null) {
          System.out.println("데이터:");
          for (String str : data) {
            System.out.println(str);
          }
        }

        if (request.equals("quit")) {
          out.writeUTF("success");
          out.writeInt(0);
          out.flush();
          break;
        }

        DataTable dataTable = null;

        Set<String> keySet = tableMap.keySet();
        for (String key : keySet) {
          if (request.startsWith(key)) {
            dataTable = tableMap.get(key);
            break;
          }
        }

        if (dataTable != null) {
          dataTable.service(request, data, out);
        } else {
          out.writeUTF("error");
          out.writeInt(1);
          out.writeUTF("해당 요청을 처리할 수 없습니다!");
        }

        out.flush();
      }

    } catch (Exception e) {
      System.out.println("클라이언트의 요청을 처리하는 중에 오류 발생!");
      e.printStackTrace();
    }
  }
}
