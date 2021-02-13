package simdo.module.qna.validator;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import simdo.module.notice.Notice;
import simdo.module.qna.form.QnaForm;


import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * @author backkwan
 */
@Service
@RequiredArgsConstructor
@Transactional
public class QnaService {

    @Autowired
    private QnaRepository qnaRepository;


    public Qna findQnaByid(long id){

        return qnaRepository.findById(id).orElse(new Qna());
    }


    public Qna saveQna(QnaForm qnaForm) {
        Qna qna = Qna.builder()
                .title(qnaForm.getSubject())
                .content(qnaForm.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        qnaRepository.save(qna);
        return qna;
    }

    public Qna qnaDetail(Long id) {
        Qna qna = qnaRepository.findQnaById(id);
        return qna;
    }


}
