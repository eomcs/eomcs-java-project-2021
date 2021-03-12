package com.eomcs.pms;

import java.net.Socket;

// 클라이언트 측의 Statement 와 연결된 후
// 그 요청 처리를 별도의 실행흐름으로 분리한다.
public class StatementHandlerThread extends Thread {
  Socket socket;

  public StatementHandlerThread(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    // 별도의 실행 흐름에서 수행할 작업이 있다면 이 메서드에 기술한다.


    super.run();
  }
}
