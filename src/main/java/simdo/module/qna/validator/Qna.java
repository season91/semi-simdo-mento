package simdo.module.qna.validator;


import lombok.*;
import simdo.module.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Table(name = "COMM")
/**
 * @author backkwan
 */
public class Qna {
    @Id
    @GeneratedValue
    @Column(name = "QSTN_NO")
    private Long id;

    @Column(name = "QSTN_TITLE")
    private String title;

    @Column(name = "QSTN_CONTENT")
    private String content;

    @Column(name = "QSTN_REG_DATE")
    private LocalDateTime createdAt;

    @Column(name = "QSTN_TYPE")
    private String type;

    @Column(name = "ATTACHEDFILE")
    private String file;

    @Column(name = "QSTN_COMENT")
    private String coment;

    @Column(name = "USER_NM")
    private String nm;


    @JoinColumn(name = "user_no")
    private long userNo;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;


}
