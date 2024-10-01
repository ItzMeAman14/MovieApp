package com.movieapp.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.movieapp.demo.Entity.movie;
import com.movieapp.demo.Repository.MovieRepository;

@Service
public class movieService{
    
    @Autowired
    private MovieRepository moviePointer;

    public movie saveMovies(movie movieToStore){

        return moviePointer.save(movieToStore);
    }
}
