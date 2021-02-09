package com.kh.simdo.movie;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

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
    private String mvNo;

    private String mvTitle;

    private String director;

    private String genre;

    private Date releaseDate;

    private String plot;

    private String nation;

    private int runtime;

    private String rating;

    private String thumbnail;

    private String poster;

}
