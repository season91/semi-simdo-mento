package simdo.movie.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simdo.movie.member.form.MemberForm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public Member signUp(MemberForm memberForm) {
        //오라클에서 못씀..
        Member newMember = Member.builder()
                .username(memberForm.getUsername())
                .email(memberForm.getEmail())
                .password(memberForm.getPassword())
                .joinedAt(LocalDateTime.now())
                .build();
        return memberRepository.save(newMember);

    }

    public List<Member> getAll() {
        return memberRepository.findAll();
    }
}
