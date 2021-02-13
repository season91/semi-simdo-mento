package simdo.module.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import simdo.module.member.Member;
import simdo.module.member.MemberService;
import simdo.module.wish.Wish;
import simdo.module.wish.WishService;

import java.util.List;
import java.util.Map;

/**
 * @author choayoung
 */

@Controller
// 매개변수 있는 생성자 어노테이션
@RequiredArgsConstructor
// uri 주소의 1뎁스부분 고정시 사용하는 어노테이션
@RequestMapping(value = "/movie")
public class MovieController {

    private final MovieService movieService;
    private final MemberService memberService;
    private final WishService wishService;

    @GetMapping(value = "/get-movie")
    public String getMovie(String title){
        // 영화 정보를 받은 후에 movie에 저장해준다.
        Map<String, Object> movieMap = movieService.kmdbAPI(title);
        String thumbnail = movieService.naverMovieAPI(title);
        movieService.saveMovie(movieMap, thumbnail);
        return "index";
    }

    // 영화 국가로 영화검색 결과 조회
    @GetMapping(value = "/nation")
    public String movieSearchByNation(Model model, String nation){
        List<Movie> movieList = movieService.movieSearchByNation(nation);
        model.addAttribute("movie",movieList);
        return "movie/result";
    }

    // 영화 장르로 영화검색 결과 조회
    @GetMapping(value = "/genre")
    public String movieSearchByGenre(Model model, String genre){
        List<Movie> movieList = movieService.movieSearchByGenre(genre);
        model.addAttribute("movie",movieList);
        return "movie/result";
    }

    // 영화 제목으로 영화검색 결과 조회
    @GetMapping(value = "/search")
    public String movieSearchByTitle(Model model, String title){
        List<Movie> movieList = movieService.movieSearchByTitle(title);
        model.addAttribute("movie",movieList);
        return "movie/result";

    }

    // 영화번호 기준으로 영화 상세 조회, 찜여부 확인을 위해 유저정보 받아오기.
    @GetMapping(value = "/detail/{email}")
    public String movieSearchByMvNo(Model model, @PathVariable String email, String mvno){
        Movie movie = movieService.movieSearchByMvNo(mvno);
        model.addAttribute("movie",movie);

        // 유저정보로 위시리스트에 정보가 있는지 확인, 찜 중이라면 찜해제버튼 보여주기 위함.
        Member member = memberService.getAccount(email);
        Wish wish = wishService.wishByMvNoUserNo(mvno, member.getId());
        model.addAttribute("wish",wish);
        return "movie/detail";
    }

    // 유저정보, 영화 번호로 찜목록에 추가, 찜목록 화면으로 이동시킬 예정. 임시로 index 화면으로 이동.
    @GetMapping(value = "/wishadd/{email}")
    public String movieWishAdd(Model model, @PathVariable String email, String mvno){
        Member member = memberService.getAccount(email);
        Movie movie = movieService.movieSearchByMvNo(mvno);
        wishService.wishSave(movie, member);
        return "index";
    }

    // 위시 번호로 찜목록 해제.
    @GetMapping(value = "/wishdel")
    public String movieWishDel(Long wishno){
        wishService.wishDel(wishno);
        return "index";
    }

    // 번역테스트
    @GetMapping(value = "/test")
    public String test(){
        return "movie/test";
    }

    // 번역테스트
    @PostMapping(value = "/translation")
    @ResponseBody
    public String translation(String text,Model model){
        String res = movieService.papagoAPI(text);
        model.addAttribute("res",res);
        return res;
    }
}
