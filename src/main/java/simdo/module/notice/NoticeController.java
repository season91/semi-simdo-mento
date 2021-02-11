package simdo.module.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import simdo.module.member.Member;
import simdo.module.notice.form.NoticeForm;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/notice")
public class NoticeController {

    private final NoticeService noticeService;

    // 관리자가 공지사항 작성할 폼.
    @GetMapping(value = "/write")
    public String noticeForm(Model model){
        model.addAttribute("noticeForm", new NoticeForm());
        return "notice/write";
    }

    // 공지사항 작성 후 DB에 저장 후 공지사항 메인화면으로 이동.
    @PostMapping(value = "/write")
    public String noticeFormSavs(NoticeForm noticeForm){
        Notice notice = noticeService.saveNotice(noticeForm);
        return "redirect:/notice";
    }

    // 공지사항 메인으로, 공지사항 리스트 출력되어야 한다.
    @GetMapping(value = "")
    public String notice(Model model){
        List<Notice> noticeList = noticeService.noticeAll();
        model.addAttribute("notice",noticeList);
        return "notice/main";
    }

    // 공지사항 상세내용 가져오기.
    @GetMapping(value = "/detail")
    public String noticeDetail(Model model, Long noticeno){
        Notice notice = noticeService.noticeDetail(noticeno);
        model.addAttribute("notice",notice);
        return "notice/detail";
    }
}
