package simdo.module.wish;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simdo.module.member.Member;
import simdo.module.movie.Movie;

import javax.transaction.Transactional;
import java.time.LocalDate;
/**
 * @author backkwan
 */
@Service
@RequiredArgsConstructor
@Transactional
public class WishService {

    private final WishRepository wishRepository;
    /**
     * @author choayoung
     */

    // 영화상세화면내에서 찜버튼 누를시 해당정보 위시에 저장.
    public void wishSave(Movie movie, Member member){
        Wish wish = Wish.builder()
                .mvNo(movie.getMvNo())
                .userNo(member.getId())
                .wishRegDate(LocalDate.now())
                .poster(movie.getPoster())
                .build();
        wishRepository.save(wish);
    }

    // 영화상세화면내에서 유저정보로 해당영화의 위시정보가 있는지 확인
    public Wish wishByMvNoUserNo(String mvNo, Long userNo){
        Wish wish = wishRepository.findByMvNoAndAndUserNo(mvNo, userNo);
        return wish;
    }

    // 위시 번호기준으로 위시 지운다.
    public void wishDel(Long wishNo){
        wishRepository.deleteByWishNo(wishNo);
    }
}
