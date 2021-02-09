package simdo.module.notice;

import lombok.*;
import simdo.module.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Table(name = "CM_NOTICE")
public class Notice {

    @Id
    @GeneratedValue
    @Column(name = "notice_no")
    private Long id;

    @Column(name = "nt_title")
    private String title;

    @Column(name = "nt_content")
    private String content;

    @Column(name = "reg_date")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private Member member;
}
