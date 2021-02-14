package simdo.module.qna;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import simdo.module.qna.form.QnaForm;

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
    public String qnaFormSave(QnaForm qnaForm){
        Qna qna = qnaService.saveQna(qnaForm);
        return "redirect:/qna/list";
    }

    //list로 가기
    @GetMapping(value = "list")
    public String qna(Model model, @PageableDefault Pageable pageable){
        Page<Qna> qna = qnaService.getQnaList(pageable);
        model.addAttribute("qna",qna);
        return "qna/list";
    }
    //상세보기화면
    @GetMapping(value = "/qnadetail")
    public String qnaDetail(Model model, Long QSTNNO){
        Qna qna = qnaService.qnaDetail(QSTNNO);
        model.addAttribute("qna",qna);
        return "qna/qnadetail";
    }


}
