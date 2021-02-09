package simdo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import simdo.module.member.CurrentMember;
import simdo.module.member.Member;

@Controller
public class MainController {
    @GetMapping(value = "/")
    public String index(@CurrentMember Member member, Model model) {
        if (member != null) {
            model.addAttribute("member", member);
            return "index-after-login";
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
