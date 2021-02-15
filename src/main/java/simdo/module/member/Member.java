package simdo.module.member;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import simdo.module.mypage.BaseTimeEntity;
import simdo.module.qna.Qna;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Table(name = "MEMBER")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String name;

    private String password;

    private String gender; //컬럼추가

    private String phone; //컬럼추가

    private LocalDate birthday; //컬럼추가

    private int emailVerified;

    private String emailCheckToken;

    private LocalDateTime emailCheckTokenGeneratedAt;

    private LocalDateTime joinedAt;

    //구글 로그인 세팅
    @Column
    private String picture;

    //구글 로그인 세팅
    @Enumerated(EnumType.STRING)
    //@Column(nullable = false)
    private Role role = Role.GUEST;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public void completeSignUp() {
        this.emailVerified = 1;
        this.joinedAt = LocalDateTime.now();
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public boolean canSendConfirmEmail() {
        return this.emailCheckTokenGeneratedAt.isBefore(LocalDateTime.now().minusHours(1));
    }

    @OneToMany(mappedBy = "member")
    private List<Qna> qnaList;

    public String getRoleKey(){
        return this.role.getKey();
    }
}
