package simdo.module.qna;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simdo.module.member.Member;
import simdo.module.qna.Qna;


/**
 * @author backkwan
 */
@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {

    //member와 qna정보 영속화
    Qna findByMember(Member member);

    Qna findQnaById(Long qnaNo);
}
