package com.movieapp.demo.Repository;

import com.movieapp.demo.Entity.movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface MovieRepository extends JpaRepository<movie, Long> {

    List<movie> findByTitleContainingIgnoreCase(String title);
}
