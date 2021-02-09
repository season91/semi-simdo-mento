package simdo.module.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

}
