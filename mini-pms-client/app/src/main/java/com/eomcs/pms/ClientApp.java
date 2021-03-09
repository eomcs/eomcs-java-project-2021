package com.eomcs.pms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import com.eomcs.util.Prompt;

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

      while (true) {
        // 1) 명령어를 보낸다.
        String message = Prompt.inputString("명령> ");
        out.writeUTF(message);

        // 2) 서버에 보낼 데이터의 개수를 보낸다.
        int no = Prompt.inputInt("개수> ");
        out.writeInt(no);

        // 3) 서버에 데이터를 보낸다.
        String parameter = Prompt.inputString("데이터> ");
        out.writeUTF(parameter);

        out.flush();


        // 서버가 보낸 데이터를 읽는다.
        // 1) 작업 결과를 읽는다.
        String response = in.readUTF();

        // 2) 데이터의 개수를 읽는다.
        int length = in.readInt();

        // 3) 데이터의 개수 만큼 읽어 List 컬렉션에 보관한다.
        ArrayList<String> data = null;
        if (length > 0) {
          data = new ArrayList<>();
          for (int i = 0; i < length; i++) {
            data.add(in.readUTF());
          }
        }

        System.out.println("----------------------------");
        System.out.printf("작업 결과: %s\n", response);
        System.out.printf("데이터 개수: %d\n", length);
        if (data != null) {
          System.out.println("데이터:");
          for (String str : data) {
            System.out.println(str);
          }
        }

        if (message.equals("quit")) {
          break;
        }
      }

      Prompt.close();

    } catch (Exception e) {
      System.out.println("서버와 통신 하는 중에 오류 발생!");
    }

  }
}
