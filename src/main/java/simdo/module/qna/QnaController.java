package simdo.module.qna;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import simdo.module.member.CurrentMember;
import simdo.module.member.Member;
import simdo.module.qna.form.QnaForm;

import java.util.List;

/**
 * @author backkwan
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/qna")
public class QnaController {

   @Autowired
   QnaService qnaService;

   //작성  post
   @GetMapping(value = "/post")
   public String postForm(Model model){
       model.addAttribute("qnaForm", new QnaForm());
       return "qna/post";
   }


    //db저장
    @PostMapping(value = "/post")
    public String qnaFormSave(@CurrentMember Member member, QnaForm qnaForm){
        Qna qna = qnaService.saveQna(member,qnaForm);
        return "redirect:/qna/list";
    }

    //list로 가기
    @GetMapping(value = "/list")
    public String qna(Model model, @CurrentMember Member member){
        List<Qna> resultList = qnaService.getQnaList(member);
        model.addAttribute("qna",resultList);
        return "qna/list";
    }
    //상세보기화면
    @GetMapping(value = "/qnadetail/{no}")
    public String qnaDetail(Model model, @PathVariable Long no){
        Qna qna = qnaService.qnaDetail(no);
        model.addAttribute("qna",qna);
        return "qna/qnadetail";
    }


}
