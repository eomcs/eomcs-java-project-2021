package com.eomcs.pms.web;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

@Controller
public class MemberAddHandler {

  MemberService memberService;

  public MemberAddHandler(MemberService memberService) {
    this.memberService = memberService;
  }

  @RequestMapping("/member/add")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    String uploadDir = request.getServletContext().getRealPath("/upload");

    if (request.getMethod().equals("GET")) {
      return "/jsp/member/form.jsp";
    }

    Member m = new Member();
    m.setName(request.getParameter("name"));
    m.setEmail(request.getParameter("email"));
    m.setPassword(request.getParameter("password"));
    m.setTel(request.getParameter("tel"));

    Part photoPart = request.getPart("photo");
    if (photoPart.getSize() > 0) {
      // 파일을 선택해서 업로드 했다면,
      String filename = UUID.randomUUID().toString();
      photoPart.write(uploadDir + "/" + filename);
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
}






