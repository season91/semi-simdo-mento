package simdo.module.mypage;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import simdo.module.member.Member;
import simdo.module.movie.Movie;
import simdo.module.movie.MovieService;
import simdo.module.mypage.fmsline.Fmsline;
import simdo.module.mypage.form.LineForm;
import simdo.module.mypage.form.ReviewForm;
import simdo.module.mypage.form.ReviewUpdateForm;
import simdo.module.mypage.review.Review;


import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/mypage")
public class MypageController {

    private final MypageService mypageService;
    private final MovieService movieService;

    @GetMapping(value = "")
    public String contentSearchByUserNo(Model model, Principal principal){
        Member activeMember = (Member)((Authentication)principal).getPrincipal();
        Long userNo = activeMember.getId();
        List<Review> reviewList = mypageService.reviewSearchByUserNo(userNo);
        List<Fmsline> lineList = mypageService.fmslineSearchByUserNo(userNo);
        model.addAttribute("review",reviewList);
        model.addAttribute("line",lineList);
        return "mypage/mywritelist";
    }

    @GetMapping(value = "/writereview")
    public String writeReview(Model model, String mvno){
        Movie movie = movieService.movieSearchByMvNo(mvno);
        model.addAttribute("movie",movie);
        return "mypage/writereview";
    }

    @PostMapping(value = "/writereview")
    public String reviewFormSave(ReviewForm reviewForm, Principal principal){
        String mvNo = reviewForm.getMvNo();
        Movie movie = movieService.movieSearchByMvNo(mvNo);
        Member activeMember = (Member)((Authentication)principal).getPrincipal();
        Long userNo = activeMember.getId();

        Review review = mypageService.saveReview(reviewForm, movie, userNo);
        return "mypage/mywritelist";
    }

    @GetMapping(value = "/writeline")
    public String writeLine(Model model, String mvno){
        Movie movie = movieService.movieSearchByMvNo(mvno);
        model.addAttribute("movie",movie);
        return "mypage/writeline";
    }

    @PostMapping(value = "/writeline")
    public String lineFormSave(LineForm lineForm, Principal principal){
        String mvNo = lineForm.getMvNo();
        Movie movie = movieService.movieSearchByMvNo(mvNo);
        Member activeMember = (Member)((Authentication)principal).getPrincipal();
        Long userNo = activeMember.getId();

        Fmsline line = mypageService.saveLine(lineForm, movie, userNo);
        return "mywritelist";
    }

    @GetMapping(value = "/updatereview")
    public String reviewUpdate(Model model, Long rvNo){
        Review review = mypageService.reviewSearchByRvNo(rvNo);
        model.addAttribute("review",review);
        return "mypage/updatereview";
    }

    @PostMapping(value = "/updatereview")
    public String reviewFormUpdate(ReviewUpdateForm reviewUpdateForm, Principal pricnipal){
        String rvNo = reviewUpdateForm.getRvNo();
        return "mywritelist";

    }
}
