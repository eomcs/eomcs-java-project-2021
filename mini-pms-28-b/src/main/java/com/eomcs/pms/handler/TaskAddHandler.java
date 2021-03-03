package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskAddHandler extends AbstractTaskHandler {

  private MemberValidatorHandler memberValidatorHandler;

  public TaskAddHandler(List<Task> taskList, MemberValidatorHandler memberValidatorHandler) {
    super(taskList);
    this.memberValidatorHandler = memberValidatorHandler;
  }

  @Override
  public void service() {
    System.out.println("[작업 등록]");

    Task t = new Task();
    t.setNo(Prompt.inputInt("번호? "));
    t.setContent(Prompt.inputString("내용? "));
    t.setDeadline(Prompt.inputDate("마감일? "));
    t.setStatus(Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> "));

    t.setOwner(memberValidatorHandler.inputMember("담당자?(취소: 빈 문자열) "));
    if (t.getOwner() == null) {
      System.out.println("작업 등록을 취소하였습니다.");
      return;
    }

    taskList.add(t);
    System.out.println("작업을 등록했습니다.");
  }
}
