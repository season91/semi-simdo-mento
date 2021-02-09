package com.kh.simdo.movie.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Data
@Getter
@Setter
@ToString
// vo 이다.
public class MovieForm {
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
