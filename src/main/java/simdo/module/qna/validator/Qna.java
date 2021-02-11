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
public class Qna {
    @Id
    @GeneratedValue
    @Column(name = "QSTN_NO")
    private Long qstnNo;

    @Column(name = "QSTN_TITLE")
    private String qstnTitle;

    @Column(name = "QSTN_CONTENT")
    private String qstnContent;

    @Column(name = "QSTN_REG_DATE")
    private LocalDateTime createdAt;

    @Column(name = "QSTN_TYPE")
    private String qstnType;

    @Column(name = "ATTACHEDFILE")
    private String attachedfile;

    @Column(name = "QSTN_COMENT")
    private String qstncoment;

    @Column(name = "USER_NM")
    private String userNm;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private Member member;
}
