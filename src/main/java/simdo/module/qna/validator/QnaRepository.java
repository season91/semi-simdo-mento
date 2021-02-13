package simdo.module.qna.validator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simdo.module.member.Member;



/**
 * @author backkwan
 */
@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {

    //member와 qna정보 영속화
    Qna findByMember(Member member);

    Qna findQnaById(Long qnaNo);
}
