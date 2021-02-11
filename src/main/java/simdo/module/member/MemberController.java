package simdo.module.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import simdo.module.member.form.SignUpForm;
import simdo.module.member.validator.SignUpFormValidator;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class MemberController {

    private final MemberService memberService;
    private final SignUpFormValidator signUpFormValidator;
    private final MemberRepository memberRepository;

    @InitBinder("signUpForm")
    public void InitBinder(WebDataBinder dataBinder) {
        dataBinder.addValidators(signUpFormValidator);
    }


    @GetMapping(value = "/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "member/sign-up";
    }


    @PostMapping(value = "/sign-up")
    public String signUp(@Valid SignUpForm signUpForm, Errors errors) {
        if (errors.hasFieldErrors("name")) {
            return "member/sign-up";
        }
        Member member = memberService.processNewMember(signUpForm);
        memberService.login(member);
        return "redirect:/";
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model) {
        Member member = memberRepository.findByEmail(email);
        String view = "member/checked-email";
        if (member == null) {
            model.addAttribute("error", "wrong.email");
            return view;
        }

        if (!member.isValidToken(token)) {
            model.addAttribute("error", "wrong.token");
            return view;
        }

        memberService.completeSignUp(member);
        model.addAttribute("numberOfUser", memberRepository.count());
        model.addAttribute("name", member.getName());
        return view;
    }

    @GetMapping("/check-email")
    public String checkEmail(@CurrentMember Member member, Model model) {
        model.addAttribute("email", member.getEmail());
        return "member/check-email";
    }

    @GetMapping("/resend-confirm-email")
    public String resendConfirmEmail(@CurrentMember Member member, Model model) {
        if (!member.canSendConfirmEmail()) {
            model.addAttribute("error", "인증 이메일은 1시간에 한번만 전송할 수 있습니다.");
            model.addAttribute("email", member.getEmail());
            return "member/check-email";
        }

        memberService.sendSignUpConfirmEmail(member);
        return "redirect:/";
    }

    @GetMapping("/profile/{email}")
    public String viewProfile(@PathVariable String email, Model model, @CurrentMember Member member) {
        Member memberToView = memberService.getAccount(email);
        model.addAttribute(memberToView);
        model.addAttribute("memberToView", memberToView); /*조민희코드추가*/
        model.addAttribute("isOwner", memberToView.equals(member));
        return "member/profile";
    }

    @GetMapping("/email-login")
    public String emailLoginForm() {
        return "account/email-login";
    }

    @PostMapping("/email-login")
    public String sendEmailLoginLink(String email, Model model, RedirectAttributes attributes) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            model.addAttribute("error", "유효한 이메일 주소가 아닙니다.");
            return "member/email-login";
        }

        if (!member.canSendConfirmEmail()) {
            model.addAttribute("error", "이메일 로그인은 1시간 뒤에 사용할 수 있습니다.");
            return "member/email-login";
        }

        memberService.sendLoginLink(member);
        attributes.addFlashAttribute("message", "이메일 인증 메일을 발송했습니다.");
        return "redirect:/email-login";
    }

    @GetMapping("/login-by-email")
    public String loginByEmail(String token, String email, Model model) {
        Member member = memberRepository.findByEmail(email);
        String view = "account/logged-in-by-email";
        if (member == null || !member.isValidToken(token)) {
            model.addAttribute("error", "로그인할 수 없습니다.");
            return view;
        }
        memberService.login(member);
        return view;
    }
}
