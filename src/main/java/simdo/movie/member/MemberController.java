package simdo.movie.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import simdo.movie.member.form.MemberForm;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping(value = "/member/sign-up")
    public String signUpForm() {
        return "member/sign-up";
    }

    @PostMapping(value = "/member/sign-up")
    public String signUp(MemberForm memberForm, Model model) {
        Member newMember = memberService.signUp(memberForm);
        model.addAttribute("newMember", newMember);
        return "member/after-sign-up";
    }

    @GetMapping(value = "/member/getall")
    @ResponseBody
    public List<Member> getAll() {
        List<Member> memberList = new ArrayList<>();
        memberList = memberService.getAll();
        return memberList;
    }
}
