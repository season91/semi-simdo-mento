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
@Table(name = "mv_basic_info")
public class Movie {
    @Id
    @Column(nullable = false, name = "MV_NO")
    private String mvNo;

    @Column(nullable = false)
    private String mvTitle;

    private Long score;

    @Column(nullable = false)
    private String director;

    private String genre;

    private LocalDate releaseDate;

    private String plot;

    private String nation;

    private Long runtime;

    private String rating;

    private String thumbnail;

    private String poster;
}
