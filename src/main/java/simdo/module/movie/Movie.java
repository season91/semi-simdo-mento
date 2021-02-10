package simdo.module.movie;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author choayoung
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "MV_BASIC_INFO")
public class Movie {
    @Id
    @Column(nullable = false, name = "MV_NO")
    private String mvNo;

    @Column(nullable = false)
    private String mvTitle;

    private String mvTitleorg;

    private Long score;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private String plot;

    @Column(nullable = false)
    private String nation;

    @Column(nullable = false)
    private Long runtime;

    @Column(nullable = false)
    private String rating;

    private String thumbnail;

    private String poster;
}
