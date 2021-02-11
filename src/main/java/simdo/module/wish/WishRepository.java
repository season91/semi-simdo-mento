package simdo.module.wish;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, String> {
    // 유저정보로 해당영화가 위시리스트에 정보가 있는지 확인
    Wish findByMvNoAndAndUserNo(String mvNo, Long userNo);

    void deleteByWishNo(Long wishNo);

}
