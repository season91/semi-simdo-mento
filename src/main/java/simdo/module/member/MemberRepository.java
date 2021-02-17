package simdo.module.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByName(String name);

    boolean existsByEmail(String email);

    //Optional<Member> findByEmail(String email);

    Member findByEmail(String email);

    Member findByName(String name);

}
