package com.kh.simdo.movie.form;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simdo.movie.member.Member;

@Repository
public interface MovieRepository  extends JpaRepository<Movie, Long> {

}
