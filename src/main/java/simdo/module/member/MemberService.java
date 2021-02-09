package simdo.module.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import simdo.module.member.form.SignUpForm;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member signUp(@Valid SignUpForm signUpForm) {
        Member member = Member.builder()
                .name(signUpForm.getName())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();
        member.processJoin();

        Member newMember = memberRepository.save(member);
        return newMember;
    }

//    이메일 인증
    public void sendVerifyingEmail(){
        Member member = memberRepository.findByName("심도도도");
        if(member.getGeneratedEmailToken().equals("asdkjaskdjaskd")){
            member.setValid(true);
        }
    }
}
