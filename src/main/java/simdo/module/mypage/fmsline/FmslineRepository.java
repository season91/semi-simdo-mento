package simdo.module.mypage.fmsline;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface FmslineRepository extends JpaRepository<Fmsline, Long> {

    @Query("SELECT f FROM Fmsline f order by f.lineNo DESC")
    List<Fmsline> findFmslineByUserNo(Long userNo);
}
