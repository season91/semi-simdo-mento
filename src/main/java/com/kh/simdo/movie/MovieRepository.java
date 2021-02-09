package com.kh.simdo.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // 얘는 다오인데, select를 쉽게 구현해준다.
    // 약간 select랑 delete를 여기서 구현해야한다.
    // 가져와서 뿌려보는거 해보기.

    // 영화번호 조회
    Movie findMovieByMvNo(String mvNo);

    // 영화 장르별 조회
    Movie findMovieByGenre(String genre);

    // 영화 나라별 조회
    List<Movie> findMovieByNation(String nation);
    List<Movie> findMovieByNationContains(String nation);

    // 영화제목별 조회
    List<Movie> findMovieByMvTitleContains(String title);
}
