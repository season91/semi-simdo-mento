package simdo.module.wish;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import simdo.module.member.Member;
import simdo.module.member.MemberService;

/**
 * @author backkwan
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/wish")
public class WishController {

    private final MemberService memberService;
    private final WishRepository wishRepository;





}
