package simdo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import simdo.infra.common.auth.SessionMember;
import simdo.module.member.Member;
import simdo.module.member.MemberService;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    private final HttpSession httpSession;
    private final MemberService memberService;

    public MainController(HttpSession httpSession, MemberService memberService) {
        this.httpSession = httpSession;
        this.memberService = memberService;
    }

    /*@GetMapping(value = "/")
    public String index(@CurrentMember Member member, Model model) {
        if (member != null) {
            model.addAttribute("member", member);
            return "index-after-login";
        }

        return "index";
    }*/
    
    //구글 로그인과 심도 로그인 유저를 구분하여 유저 정보를 받아와야 해서 수정
    @GetMapping(value = "/")
    public String index(Model model) {
        try{
            SessionMember sessionMember = (SessionMember) httpSession.getAttribute("member");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (sessionMember != null) {
                System.out.println(sessionMember.toString());
                model.addAttribute("member", sessionMember);
                return "index-after-login";
            }else if (!auth.getName().equals("anonymousUser")) {
                Member member = memberService.getAccount(auth.getName());
                model.addAttribute("member", member);
                return "index-after-login";
            }
            return "index";
        }catch (IllegalArgumentException e) {
            return "index";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
