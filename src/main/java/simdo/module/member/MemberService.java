package simdo.module.member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import simdo.infra.config.AppProperties;
import simdo.infra.mail.EmailMessage;
import simdo.infra.mail.EmailService;
import simdo.module.member.form.SignUpForm;
import simdo.module.member.form.UpdateForm;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TemplateEngine templateEngine;
    private final EmailService emailService;
    private final AppProperties appProperties;

    public Member processNewMember(SignUpForm signUpForm) {
        Member newMember = saveNewMember(signUpForm);
        sendSignUpConfirmEmail(newMember);
        return newMember;
    }

    private Member saveNewMember(@Valid SignUpForm signUpForm) {
        Member member = Member.builder()
                .name(signUpForm.getName())
                .email(signUpForm.getEmail())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();
        member.generateEmailCheckToken();
        return memberRepository.save(member);
    }

    public void sendSignUpConfirmEmail(Member newMember) {
        Context context = new Context();
        context.setVariable("link", "/member/check-email-token?token=" + newMember.getEmailCheckToken() +
                "&email=" + newMember.getEmail());
        context.setVariable("name", newMember.getName());
        context.setVariable("linkName", "이메일 인증하기");
        context.setVariable("message", "심도 서비스를 사용하려면 링크를 클릭하세요.");
        context.setVariable("host", appProperties.getHost());
        String message = templateEngine.process("mail/simple-link", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .to(newMember.getEmail())
                .subject("심도, 회원 가입 인증")
                .message(message)
                .build();

        emailService.sendEmail(emailMessage);
    }

    public void sendLoginLink(Member member) {
        Context context = new Context();
        context.setVariable("link", "/member/login-by-email?token=" + member.getEmailCheckToken() +
                "&email=" + member.getEmail());
        context.setVariable("name", member.getName());
        context.setVariable("linkName", "심도 로그인하기");
        context.setVariable("message", "로그인 하려면 아래 링크를 클릭하세요.");
        context.setVariable("host", appProperties.getHost());
        String message = templateEngine.process("mail/simple-link", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .to(member.getEmail())
                .subject("심도, 로그인 링크")
                .message(message)
                .build();
        emailService.sendEmail(emailMessage);
    }


    public void login(Member member) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(member),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_MEMBER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new UsernameNotFoundException(email);
        }
        return new UserAccount(member);
    }

    public void completeSignUp(Member member) {
        member.completeSignUp();
        login(member);
    }

    public Member getAccount(String email) {
//        Member member = memberRepository.findByName(name);  멘토님 코드
        Member member = memberRepository.findByEmail(email); /*조민희 코드 추가*/
        if (member == null) {
            throw new IllegalArgumentException(email + "에 해당하는 사용자가 없습니다.");
        }
        return member;
    }

    public void updatePassword(Member member, String newPassword) {
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    public void quit(Member memberToQuit) {
        memberRepository.deleteById(memberToQuit.getId());
    }

    public void updateInfo(Member memberToUpdate, @Valid UpdateForm updateForm) {
        if (updateForm.getName() != "") {
            memberToUpdate.setName(updateForm.getName());
        }

        if (updateForm.getPassword() != "") {
            updatePassword(memberToUpdate, memberToUpdate.getPassword());
        }

        if (updateForm.getPhone() != "") {
            memberToUpdate.setPhone(updateForm.getPhone());
        }

        if (updateForm.getGender() != "") {
            memberToUpdate.setGender(updateForm.getGender());
        }

        if (updateForm.getBirthday() != "") {
            LocalDate parsedBirthday = LocalDate.parse(updateForm.getBirthday());
            memberToUpdate.setBirthday(parsedBirthday);
        }
    }
}
