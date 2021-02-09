package com.kh.simdo.movie;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class MovieTest {

    private String mvNo;

    private String mvTitle;

    private int score;

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
