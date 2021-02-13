package simdo.module.review;

import lombok.*;


import javax.persistence.*;


/**
 * @author jonghwan
 */
@Getter
//@Setter
//@AllArgsConstructor
@NoArgsConstructor
//@Builder
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

    private String rvContent;

    private String watchDate;

    @Column(nullable = false)
    private String mvNo;

    private String mvTitle;

    @Column(nullable = false)
    private String thumbnail;

    @Builder
    public Review(Long userNo, double score, String rvContent, String watchDate, String mvNo,
                  String mvTitle, String thumbnail){
        this.userNo = userNo;
        this.score = score;
        this.rvContent = rvContent;
        this.watchDate = watchDate;
        this.mvNo = mvNo;
        this.mvTitle = mvTitle;
        this.thumbnail = thumbnail;
    }


}
