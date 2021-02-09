package com.kh.simdo.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {
    // 트랜잭션단위로 바로 디비로 값을 넣어준다,
    // 레파지토리 값 가져온다.
    private final MovieRepository movieRepository;

    public List<Movie> navi(String nation){

        return movieRepository.findMovieByNation(nation);
    }

    public List<Movie> movieSearchByNation(String nation){
        return  movieRepository.findMovieByNationContains(nation);
    }
    public List<Movie> movieSearchByTitle(String title){
        return movieRepository.findMovieByMvTitleContains(title);
    }
    public Movie movieDetailByMvNo(String mvNo){
        return  movieRepository.findMovieByMvNo(mvNo);
    }

}
