package simdo.module.wish;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import simdo.module.member.MemberService;

/**
 * @author backkwan
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/wish")
public class WishController {

    private final MemberService memberService;

    private final WishService wishService;

    //email정보에 기반해서 위시리스트 리스트불러오기
    @GetMapping(value = "/wishlist/{email}")
    public String wish(Model model){

        Wish wishList = wishService.getWishList;
        model.addAttribute("wishList",wishList);
        return "wishlist";

    }


}
