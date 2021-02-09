package simdo.movie.member;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
//시퀀스 생성
@SequenceGenerator(name = "SEQ_MEMBER", sequenceName = "SEQ_MEMBER", initialValue = 1001, allocationSize = 10)

//테이블명 지정
@Table(name = "TB_MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEMBER")
    @Column(name = "member_id")
    private Long id;

    private String username;

    @Column(unique = true)
    @Email
    private String email;

    private String password;

    private LocalDateTime joinedAt;
}
