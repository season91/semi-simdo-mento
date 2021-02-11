package simdo.module.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    // 공지사항 번호로 상세내역 확인
    Notice findNoticeById(Long noticeNo);


}
