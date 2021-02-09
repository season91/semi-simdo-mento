package simdo.module.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "persistent_logins")
@Entity
@Getter
@Setter
public class PersistentLogins {

//    Spring Security가 관리해주는 Remember me 를 사용하기 위해서 Entity를 생성하고, DB로 관리합니다.
//    Remember me 에 체크하고 로그인을 하면, 로그아웃 하기 전까지 자동 로그인을 지원합니다.

    @Id
    @Column(length = 64)
    private String series;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(name = "last_used", nullable = false, length = 64)
    private LocalDateTime lastUsed;

}
