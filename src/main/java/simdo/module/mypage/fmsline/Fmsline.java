package simdo.module.mypage.fmsline;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "USER_FMSLINE")
public class Fmsline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FMSLINE_NO")
    private Long lineNo;

    @Column(nullable = false)
    private Long userNo;

    @Column(length = 500, nullable = false)
    private String fmlContent;

    @Column(nullable = false)
    private String mvNo;

    private String mvTitle;

    @Column(nullable = false)
    private String thumbnail;
}
