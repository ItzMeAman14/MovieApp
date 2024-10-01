package com.movieapp.demo.Controller;

import com.movieapp.demo.Entity.movie;
import com.movieapp.demo.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class movieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) String search) {
        List<movie> movies;

        if (search != null && !search.isEmpty()) {
            movies = movieRepository.findByTitleContainingIgnoreCase(search);
            model.addAttribute("search", search);
        } else {
            movies = movieRepository.findAll();
        }

        model.addAttribute("movies", movies);
        return "index";
    }

    @GetMapping("/addMovie")
    public String showAddMovieForm() {
        return "addMovie";
    }

    @PostMapping("/addMovie")
    public String addMovie(@RequestParam String title,
                           @RequestParam String poster,
                           @RequestParam String description) {
        movie obj = new movie();
        obj.setTitle(title);
        obj.setPoster(poster);
        obj.setDescription(description);
        movieRepository.save(obj);
        return "redirect:/";
    }


    @GetMapping("/movie/{id}")
    public String getMovieDetails(@PathVariable Long id, Model model) {
        movie movie = movieRepository.findById(id).orElse(null);
        model.addAttribute("movie", movie);
        return "movieDetail"; // Create a movieDetails.html file in src/main/resources/templates
    }

}
