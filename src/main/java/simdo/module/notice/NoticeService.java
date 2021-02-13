package simdo.module.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simdo.module.notice.form.NoticeEditForm;
import simdo.module.notice.form.NoticeForm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author choayoung
 */
@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 공지사항 작성 후 저장하기.
    public void saveNotice(NoticeForm noticeForm){
        Notice notice = Notice.builder()
                .title(noticeForm.getSubject())
                .content(noticeForm.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        noticeRepository.save(notice);
    }

    // 공지사항 수정 후 저장하기
    public Notice saveNoticeEdit(Notice notice, NoticeEditForm noticeEditForm){
        notice.setTitle(noticeEditForm.getSubject());
        notice.setContent(noticeEditForm.getContent());
        Notice noticeEdit = noticeRepository.save(notice);
        return noticeEdit;
    }

    // 공지사항 개수 가져오기. 게시판 페이징처리는 멘토님께서 도와주신다고 하셨는데 문의해보기!
    public List<Notice> noticeAll(){
        List<Notice> noticeList =  noticeRepository.findAll();
        return noticeList;
    }

    // 공지사항 번호 기준으로 공지 1개 가져오기.
    public Notice findNoticeById(Long noticeNo){
        Notice notice = noticeRepository.findNoticeById(noticeNo);
        return notice;
    }

    // 페이징 처리
    public Page<Notice> getNoticeList(Pageable pageable){
        return  noticeRepository.findAll(pageable);
    }

    // 공지사항 삭제
    public void noticeDelete(Long noticeNo){
        noticeRepository.deleteById(noticeNo);
    }

    // 공지사항 수정

}
