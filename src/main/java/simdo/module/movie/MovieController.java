package simdo.module.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author choayoung
 */

@Controller
// 매개변수 있는 생성자 어노테이션
@RequiredArgsConstructor
@RequestMapping(value = "/movie")
public class MovieController {

    private final MovieService movieService;

    @GetMapping(value = "/get-movie")
    public String getMovie(){
        // 영화 정보를 받은 후에 movie에 저장해준다.
        Map<String, Object> movieMap = movieService.kmdbAPI();
        String thumbnail = movieService.naverMovieAPI();
        movieService.saveMovie(movieMap, thumbnail);
        return "index";
    }

    @GetMapping(value = "/nation")
    public String movieSearchByNation(Model model, String nation){
        List<Movie> movieList = movieService.movieSearchByNation(nation);
        model.addAttribute("movie",movieList);
        return "movie/result";
    }

    @GetMapping(value = "/genre")
    public String movieSearchByGenre(Model model, String genre){
        List<Movie> movieList = movieService.movieSearchByGenre(genre);
        model.addAttribute("movie",movieList);
        return "movie/result";
    }

    @GetMapping(value = "/search")
    public String movieSearchByTitle(Model model, String title){
        List<Movie> movieList = movieService.movieSearchByTitle(title);
        model.addAttribute("movie",movieList);
        return "movie/result";

    }

    @GetMapping(value = "/detail")
    public String movieSearchByMvNo(Model model, String mvNo){
        Movie movie = movieService.movieSearchByMvNo(mvNo);
        model.addAttribute("movie",movie);
        return "movie/detail";
    }

}
