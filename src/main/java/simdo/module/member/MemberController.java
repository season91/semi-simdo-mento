package simdo.module.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import simdo.module.member.form.SignUpForm;
import simdo.module.member.validator.SignUpFormValidator;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class MemberController {

    private final MemberService memberService;
    private final SignUpFormValidator signUpFormValidator;

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
        System.out.println("signUpForm = " + signUpForm.getName());
        Member member = memberService.signUp(signUpForm);
        System.out.println("member.getPassword() = " + member.getPassword());
        return "member/sign-up";
    }
}
