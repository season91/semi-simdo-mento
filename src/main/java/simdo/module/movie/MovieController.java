package simdo.module.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping(value = "/get-movie")
    public String getMovie(){
        // 영화 정보를 받은 후에 movie에 저장해준다.
        Map<String, Object> movieMap = movieService.kmdbAPI();
        String thumbnail = movieService.naverMovieAPI();
        movieService.saveMovie(movieMap, thumbnail);
        return "index";
    }



}
