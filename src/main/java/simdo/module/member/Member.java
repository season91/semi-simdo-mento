package simdo.module.member;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Table(name = "USER")
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    private String bio;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

}
