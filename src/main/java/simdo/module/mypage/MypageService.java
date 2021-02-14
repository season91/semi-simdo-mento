package simdo.module.mypage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simdo.module.movie.Movie;
import simdo.module.mypage.fmsline.Fmsline;
import simdo.module.mypage.fmsline.FmslineRepository;
import simdo.module.mypage.form.LineForm;
import simdo.module.mypage.form.ReviewForm;
import simdo.module.mypage.review.Review;
import simdo.module.mypage.review.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MypageService {

    private final ReviewRepository reviewRepository;
    private final FmslineRepository fmslineRepository;

    public List<Review> reviewSearchByUserNo(Long userNo){
        return reviewRepository.findReviewByUserNo(userNo);
    }

    public List<Fmsline> fmslineSearchByUserNo(Long userNo){
        return fmslineRepository.findFmslineByUserNo(userNo);
    }

    public Review saveReview(ReviewForm reviewForm, Movie movie, Long userNo) {
        Review review = Review.builder()
                .userNo(userNo)
                .score(reviewForm.getScore())
                .rvContent(reviewForm.getContent())
                .watchDate(reviewForm.getWatchDate())
                .mvNo(movie.getMvNo())
                .mvTitle(movie.getMvTitle())
                .thumbnail(movie.getThumbnail())
                .build();
        reviewRepository.save(review);
        return review;
    }

    public Fmsline saveLine(LineForm lineForm, Movie movie, Long userNo) {
        Fmsline line = Fmsline.builder()
                .userNo(userNo)
                .fmlContent(lineForm.getContent())
                .mvNo(movie.getMvNo())
                .mvTitle(movie.getMvTitle())
                .thumbnail(movie.getThumbnail())
                .build();
        fmslineRepository.save(line);
        return line;
    }

    public Review reviewSearchByRvNo(Long rvNo){
        Review review = (Review)reviewRepository.findReviewByRvNo(rvNo);

        return review;
    }

}
