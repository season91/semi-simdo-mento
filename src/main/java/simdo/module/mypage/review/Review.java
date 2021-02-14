package simdo.module.mypage.review;

import lombok.*;
import simdo.module.mypage.BaseTimeEntity;


import javax.persistence.*;


/**
 * @author jonghwan
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "USER_REVIEW")
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_NO")
    private Long rvNo;

    @Column(nullable = false)
    private Long userNo;

    private double score;

    @Column(length = 500, nullable = false)
    private String rvContent;

    @Column(nullable = false)
    private String watchDate;

    @Column(nullable = false)
    private String mvNo;

    private String mvTitle;

    @Column(nullable = false)
    private String thumbnail;



}
