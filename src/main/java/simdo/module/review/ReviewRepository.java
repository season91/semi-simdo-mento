package simdo.module.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jonghwan
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
}
