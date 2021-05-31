package com.eomcs.pms.web;

import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

@Controller
@RequestMapping("/member/")
public class MemberController {

  ServletContext sc;
  MemberService memberService;

  public MemberController(MemberService memberService, ServletContext sc) {
    this.memberService = memberService;
    this.sc = sc;
  }

  @GetMapping("form")
  public void form() throws Exception {
  }

  @PostMapping("add")
  public String add(Member m, Part photoFile) throws Exception {
    String uploadDir = sc.getRealPath("/upload");

    if (photoFile.getSize() > 0) {
      // 파일을 선택해서 업로드 했다면, 
      String filename = UUID.randomUUID().toString();
      photoFile.write(uploadDir + "/" + filename);
      m.setPhoto(filename);

      // 썸네일 이미지 생성
      Thumbnails.of(uploadDir + "/" + filename)
      .size(30, 30)
      .outputFormat("jpg")
      .crop(Positions.CENTER)
      .toFiles(new Rename() {
        @Override
        public String apply(String name, ThumbnailParameter param) {
          return name + "_30x30";
        }
      });

      Thumbnails.of(uploadDir + "/" + filename)
      .size(80, 80)
      .outputFormat("jpg")
      .crop(Positions.CENTER)
      .toFiles(new Rename() {
        @Override
        public String apply(String name, ThumbnailParameter param) {
          return name + "_80x80";
        }
      });
    }

    memberService.add(m);
    return "redirect:list";
  }

  @GetMapping("delete")
  public String delete(int no) throws Exception {
    Member member = memberService.get(no);
    if (member == null) {
      throw new Exception("해당 번호의 회원이 없습니다.");
    }

    // 회원 관리를 관리자가 할 경우 모든 회원의 정보 변경 가능
    //      Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    //      if (oldBoard.getWriter().getNo() != loginUser.getNo()) {
    //        throw new Exception("삭제 권한이 없습니다!");
    //      }

    memberService.delete(no);
    return "redirect:list";
  }

  @GetMapping("detail")
  public void detail(int no, Model model) throws Exception {
    Member m = memberService.get(no);
    model.addAttribute("member", m);
  }

  @GetMapping("list") 
  public void list(String keyword, Model model) throws Exception {
    List<Member> list = memberService.list(keyword);
    model.addAttribute("list", list);
  }

  @PostMapping("update")
  public String update(Member member, Part photoFile) throws Exception {
    String uploadDir = sc.getRealPath("/upload");

    // 회원 관리를 관리자가 할 경우 모든 회원의 정보 변경 가능
    //      Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    //      if (oldMember.getNo() != loginUser.getNo()) {
    //        throw new Exception("변경 권한이 없습니다!");
    //      }

    if (photoFile.getSize() > 0) {
      // 파일을 선택해서 업로드 했다면,
      String filename = UUID.randomUUID().toString();
      photoFile.write(uploadDir + "/" + filename);
      member.setPhoto(filename);

      // 썸네일 이미지 생성
      Thumbnails.of(uploadDir + "/" + filename)
      .size(30, 30)
      .outputFormat("jpg")
      .crop(Positions.CENTER)
      .toFiles(new Rename() {
        @Override
        public String apply(String name, ThumbnailParameter param) {
          return name + "_30x30";
        }
      });

      Thumbnails.of(uploadDir + "/" + filename)
      .size(80, 80)
      .outputFormat("jpg")
      .crop(Positions.CENTER)
      .toFiles(new Rename() {
        @Override
        public String apply(String name, ThumbnailParameter param) {
          return name + "_80x80";
        }
      });
    }

    if (memberService.update(member) == 0) {
      throw new Exception("해당 번호의 회원이 없습니다.");
    } 

    return "redirect:list";
  }
}






