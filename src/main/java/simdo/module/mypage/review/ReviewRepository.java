package simdo.module.mypage.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import simdo.module.mypage.review.Review;


import java.util.List;

/**
 * @author jonghwan
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("SELECT r FROM Review r order by r.rvNo DESC")
    List<Review> findReviewByUserNo(Long userNo);

    Review findReviewByRvNo(Long rvNo);
}
