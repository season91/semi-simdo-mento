package simdo.module.qna;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import simdo.module.member.CurrentMember;
import simdo.module.member.Member;
import simdo.module.member.MemberRepository;
import simdo.module.qna.form.QnaForm;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author backkwan
 */
@Service
@RequiredArgsConstructor
@Transactional
public class QnaService {

    private final QnaRepository qnaRepository;
    private final MemberRepository memberRepository;


    public Qna saveQna(@CurrentMember Member member, QnaForm qnaForm) {
        Qna qna = Qna.builder()
                .title(qnaForm.getSubject())
                .content(qnaForm.getContent())
                .createdAt(LocalDateTime.now())
                .member(member)
                .build();
        qnaRepository.save(qna);
        return qna;
    }

    public Qna qnaDetail(Long no) {
        Optional<Qna> findQna = qnaRepository.findById(no);
        return findQna.get();
    }


    public List<Qna> getQnaList(@CurrentMember Member member) {
        Member findMember = memberRepository.findByName(member.getName());
        System.out.println("findMember.getName() = " + findMember.getName());

        return findMember.getQnaList();
    }
}

