package com.eomcs.util.concurrent;

import java.util.LinkedList;
import java.util.List;

// 스레드풀 역할을 수행한다.\
// - 스레드를 생성하고 유지한다.
// - 스레드의 재사용을 관리한다.
// 
public class ThreadPool {

  int threadCount;

  // 스레드의 상태를 설정한다.
  boolean isStop;

  // 스레드에게 작업을 주면 실행을 하도록 기존 스레드를 '입맛에 맞게 변경(customizing)'한다.
  class Executor extends Thread {
    Runnable task;

    public Executor(int threadNo) {
      super(threadNo + " 번");
    }

    public void setTask(Runnable task) {
      // 스레드에 작업을 할당하면 '작업하라'는 알림을 보낸다.
      synchronized(this) {
        this.task = task;
        this.notify();
      }
    }

    @Override
    public void run() {
      while (true) {
        synchronized (this) {
          try {
            // `작업하라`는 알림이 올 때까지 이 스레드는 기다린다.
            this.wait();

            if (isStop) {
              break; // 반복문을 나가고, run() 메서드를 종료하면 스레드는 멈춘다!
            }
          } catch (Exception e) {
            System.out.println("스레드를 대기시키는 중에 오류 발생!");
            break;
          }

          // 작업을 실행한다.
          this.task.run();

          // 스레드의 작업이 끝난 후 스레드를 종료하라는 상태라면,
          // 즉시 run() 메서드를 나간다.
          if (isStop) {
            break;
          }

          // 작업이 끝난 후 자신을 스레드풀로 돌려 보낸다.
          returnExecutor(this);
        }
      }

      System.out.println(this.getName() + " 스레드 종료!");
    }
  }

  // 스레드 목록
  List<Executor> executors = new LinkedList<>();

  // 스레드를 실행한다.
  public void execute(Runnable task) {
    // 스레드 목록에서 스레드를 꺼낸다.
    Executor executor = getExecutor();
    System.out.println(executor.getName() + " 스레드 사용!");

    // 스레드에 작업을 할당한다.
    executor.setTask(task);
  }

  private Executor getExecutor() {
    if (executors.size() == 0) {
      // 스레드 목록에 스레드가 없다면 새로 스레드를 만든다.
      Executor executor = new Executor(++threadCount);
      System.out.println(threadCount + " 번 스레드 생성!");

      // 새로 만든 스레드를 일단 시작시킨다. 
      // => 실제 스레드는 작업을 할당하기 전까지 기다릴 것이다.
      executor.start();

      // 스레드가 실행되어 스스로 대기 상태로 될 때까지 main 스레드를 잠시 멈춘다.
      try {Thread.sleep(200);} catch (Exception e) {}

      return executor;
    }

    // 스레드 목록에 기존에 반납된 스레드가 있다면 그 스레드를 리턴한다.
    // => 스레드 재사용!
    return executors.remove(0);
  }

  private void returnExecutor(Executor executor) {
    executors.add(executor);
  }

  public void shutdown() {
    // 스레드의 실행 상태를 종료 상태로 설정한다.
    isStop = true;

    // 스레드 목록에 들어 있는 모든 스레드를 깨운다.
    // => 스레드가 깨어나면 제일 먼저 isStop 변수의 값을 검사하여 
    //    true이면 스레드를 멈출 것이다.
    for (Executor executor : executors) {
      executor.setTask(null); // 터치!!
    }
  }
}











