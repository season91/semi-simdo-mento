package com.kh.simdo.movie;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


/**
 * @author choayoung
 */
@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    //컨트롤러가 서블릿처럼 경로지정 매핑해줘야하는 곳.
    @GetMapping(value = "/movie/main")
    public String movieMain(){
        return "movie/main";
    }

    @GetMapping(value = "/movie/nation")
    public String movieSearchByNation(Model model, String nation){
        List<Movie> movieList = movieService.movieSearchByNation(nation);
        model.addAttribute("movie",movieList);
        return "movie/naviview";
    }

    @GetMapping(value = "/movie/detail")
    public String movieSearchByMvNo(Model model, String mvno){
        Movie movie = movieService.movieDetailByMvNo(mvno);
        model.addAttribute("movie",movie);
        return "movie/detail";
    }

    @GetMapping(value = "/movie/search")
    public String movieSearchByTitle(Model model, String title){
        List<Movie> movieList = movieService.movieSearchByTitle(title);
        model.addAttribute("movie",movieList);
        return "movie/search";
    }

}
