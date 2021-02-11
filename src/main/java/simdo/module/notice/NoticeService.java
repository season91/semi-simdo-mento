package simdo.module.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simdo.module.notice.form.NoticeForm;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public Notice saveNotice(NoticeForm noticeForm){
        Notice notice = Notice.builder()
                .title(noticeForm.getSubject())
                .content(noticeForm.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        noticeRepository.save(notice);
        return notice;
    }

    public List<Notice> noticeAll(){
        List<Notice> noticeList =  noticeRepository.findAll();
        return noticeList;
    }

}
