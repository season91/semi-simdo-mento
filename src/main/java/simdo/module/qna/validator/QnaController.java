package simdo.module.qna.validator;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import simdo.module.notice.Notice;
import simdo.module.notice.form.NoticeForm;
import simdo.module.qna.form.QnaForm;

import java.awt.print.Pageable;

/**
 * @author backkwan
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/qna")
public class QnaController {

   @Autowired
   QnaService qnaService;

    //작성post
    @GetMapping({"./"})
    public String qna(@RequestParam(value="id",defaultValue = "0")Long id,
                                Model model) {
        model.addAttribute("qna", qnaService.findQnaByid(id));
        return "/qna/post";
    }
    //db저장
    @PostMapping(value = "/post")
    public String qnaFormSave(QnaForm qnaForm){
        Qna qna = qnaService.saveQna(qnaForm);
        return "redirect:/post";
    }
    //상세보기화면
    @GetMapping(value = "/qnadetail")
    public String qnaDetail(Model model, Long qstnNo){
        Qna qna = qnaService.qnaDetail(qstnNo);
        model.addAttribute("qna",qna);
        return "qna/qnadetail";
    }


}
