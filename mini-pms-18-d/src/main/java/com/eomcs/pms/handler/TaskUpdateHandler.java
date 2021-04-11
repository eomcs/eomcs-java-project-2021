package com.eomcs.pms.handler;

import java.sql.Date;
import java.util.List;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskUpdateHandler extends AbstractTaskHandler {

  private MemberValidatorHandler memberValidatorHandler;

  public TaskUpdateHandler(List<Task> taskList, MemberValidatorHandler memberValidatorHandler) {
    super(taskList);
    this.memberValidatorHandler = memberValidatorHandler;
  }

  @Override
  public void service() {
    System.out.println("[작업 변경]");

    int no = Prompt.inputInt("번호? ");

    Task task = findByNo(no);
    if (task == null) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }

    String content = Prompt.inputString(String.format("내용(%s)? ", task.getContent()));
    Date deadline = Prompt.inputDate(String.format("마감일(%s)? ", task.getDeadline()));
    int status = Prompt.inputInt(String.format(
        "상태(%s)?\n0: 신규\n1: 진행중\n2: 완료\n> ", getStatusLabel(task.getStatus())));
    String owner = memberValidatorHandler.inputMember(String.format("담당자(%s)?(취소: 빈 문자열) ", task.getOwner()));
    if(owner == null) {
      System.out.println("작업 변경을 취소합니다.");
      return;
    }

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      task.setContent(content);
      task.setDeadline(deadline);
      task.setStatus(status);
      task.setOwner(owner);
      System.out.println("작업을 변경하였습니다.");

    } else {
      System.out.println("작업 변경을 취소하였습니다.");
    }
  }
}
