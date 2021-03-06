package simdo.module.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import simdo.module.member.Member;
import simdo.module.notice.form.NoticeEditForm;
import simdo.module.notice.form.NoticeForm;

import java.util.List;
/**
 * @author choayoung
 */

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
    public String noticeFormSave(NoticeForm noticeForm){
        noticeService.saveNotice(noticeForm);
        return "redirect:/notice";
    }

    // 공지사항 메인으로, 공지사항 리스트 출력되어야 한다.
    @GetMapping(value = "")
    public String notice(Model model, @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Notice> notice = noticeService.getNoticeList(pageable);
        model.addAttribute("notice",notice);
        return "notice/main";
    }

    // 공지사항 번호 기준으로 상세내용 가져오기.
    @GetMapping(value = "/detail")
    public String noticeDetail(Model model, Long noticeno){
        Notice notice = noticeService.findNoticeById(noticeno);
        model.addAttribute("notice",notice);
        return "notice/detail";
    }


    // 공지사항 수정 작성할 폼
    @GetMapping(value = "/edit")
    public String noticeEdit(Model model, Long noticeno){
        Notice notice = noticeService.findNoticeById(noticeno);
        model.addAttribute("notice",notice);
        model.addAttribute("noticeEditForm",new NoticeEditForm());
        return "notice/edit";
    }

    // 공지사항 수정 후 DB 저장 후 수정내용 화면에 보여주기.
    @PostMapping(value = "/edit")
    public String noticeEditFormSave(Model model, NoticeEditForm noticeEditForm, Long noticeno, RedirectAttributes redirectAttributes){
        Notice notice = noticeService.findNoticeById(noticeno);
        Notice noticeEdit = noticeService.saveNoticeEdit(notice, noticeEditForm);
        model.addAttribute("notice",noticeEdit);
        redirectAttributes.addAttribute("noticeno",noticeno);
        return "redirect:/notice/detail";
    }

    
    // 공지사항 번호 기준으로 삭제하기
    @GetMapping(value = "/delete")
    public String noticeDelete(Long noticeno){
        noticeService.noticeDelete(noticeno);
        return "redirect:/notice";
    }

}
