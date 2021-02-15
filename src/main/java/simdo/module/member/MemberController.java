package simdo.module.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import simdo.infra.common.auth.SessionMember;
import simdo.module.member.form.SignUpForm;
import simdo.module.member.form.UpdateForm;
import simdo.module.member.validator.SignUpFormValidator;
import simdo.module.member.validator.UpdateFormValidator;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class MemberController {

    private final MemberService memberService;
    private final SignUpFormValidator signUpFormValidator;
    private final MemberRepository memberRepository;
    private final UpdateFormValidator updateFormValidator;
    private final HttpSession httpSession;

    @InitBinder("signUpForm")
    public void InitBinder(WebDataBinder dataBinder) {
        dataBinder.addValidators(signUpFormValidator);
    }

    @InitBinder("updateForm")
    public void UpdateFormInitBinder(WebDataBinder dataBinder) {
        dataBinder.addValidators(updateFormValidator);
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
    public String viewProfile(@PathVariable String email, Model model, HttpServletResponse response) throws IOException {
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("member");
        if (sessionMember != null) {
            Member memberToView = memberService.getAccount(sessionMember.getEmail());
            model.addAttribute(memberToView);
            model.addAttribute("updateForm", new UpdateForm());
            model.addAttribute("memberToView", memberToView); /*조민희코드추가*/
            model.addAttribute("isOwner", memberToView.equals(sessionMember));

            return "member/profile";
        }else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Member member = memberService.getAccount(auth.getName());
            Member memberToView = memberService.getAccount(email);
            model.addAttribute(memberToView);
            model.addAttribute("updateForm", new UpdateForm());
            model.addAttribute("memberToView", memberToView); /*조민희코드추가*/
            model.addAttribute("isOwner", memberToView.equals(member));

            return "member/profile";
        }
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

    @PostMapping("/quit/{email}")
    public void memberQuit(@PathVariable String email, Model model, HttpServletResponse response) throws IOException {
        try {
            Member memberToQuit = memberService.getAccount(email);
            model.addAttribute(memberToQuit);
            model.addAttribute("memberToView", memberToQuit);
            memberService.quit(memberToQuit);
            SecurityContextHolder.clearContext();

            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('회원 탈퇴가 완료되었습니다.'); location.href='/';</script>");
            out.flush();
        }catch (IllegalArgumentException e) {
            SessionMember sessionMember = (SessionMember) httpSession.getAttribute("member");
            Member memberToQuit = memberService.getAccount(sessionMember.getEmail());
            model.addAttribute(memberToQuit);
            model.addAttribute("memberToView", memberToQuit);
            memberService.quit(memberToQuit);
            SecurityContextHolder.clearContext();
            httpSession.removeAttribute("member");

            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('회원 탈퇴가 완료되었습니다.'); location.href='/';</script>");
            out.flush();
        }
    }

    @PostMapping("/update-info/{email}")
    public void memberUpdate(@PathVariable String email, Model model, @Valid UpdateForm updateForm, BindingResult result, HttpServletResponse response) throws IOException {
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("member");
        Member memberToUpdate;
        if (sessionMember != null) {
            memberToUpdate = memberService.getAccount(sessionMember.getEmail());
        }else {
            memberToUpdate = memberService.getAccount(email);
        }

        if (result.hasErrors()) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('[정보 변경 실패] 이름을 공백없이 문자와 숫자로만 2자 이상 8자 이내로 입력하셔야 합니다.'); history.back();</script>");
            out.flush();
        } else {
            model.addAttribute(memberToUpdate);
            model.addAttribute("member", memberToUpdate);
            memberService.updateInfo(memberToUpdate, updateForm);

            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('정보 변경이 완료되었습니다.'); location.href='/';</script>");
            out.flush();
        }
    }

    @GetMapping("/find-password")
    public String findPassword(){
        return "member/find-password";
    }

    @PostMapping("/find-password")
    public String sendTempPassword(@RequestParam(value = "email") String email, Model model, HttpServletResponse response) throws IOException {
        try{
            Member memberToFindPassword = memberService.getAccount(email);
            model.addAttribute(memberToFindPassword);
            memberService.sendTempPasswordEmail(memberToFindPassword);
            return "redirect:/login";
        } catch (IllegalArgumentException e){
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('가입되지 않은 이메일입니다.'); location.href='/';</script>");
            out.flush();
            return "redirect:/";
        }
    }
}
