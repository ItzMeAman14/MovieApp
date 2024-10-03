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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class movieController {

    @Autowired
    private MovieRepository movieRepository;

    private final String UPLOAD_DIR = "src/main/resources/static/images/";

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
                           @RequestParam MultipartFile poster,
                           @RequestParam String description) {
        movie obj = new movie();
        obj.setTitle(title);
        
        if (!poster.isEmpty()) {
            try {
                File dir = new File(UPLOAD_DIR);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                
                String filePath = UPLOAD_DIR + poster.getOriginalFilename();
                Path path = Paths.get(filePath);
                Files.write(path, poster.getBytes());

                obj.setPoster("images/" + poster.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        obj.setDescription(description);
        movieRepository.save(obj);
        return "redirect:/";
    }

    @GetMapping("/movie/{id}")
    public String getMovieDetails(@PathVariable Long id, Model model) {
        Optional<movie> movieDetail = movieRepository.findById(id);
        
        if (movieDetail.isPresent()) {
            model.addAttribute("movie", movieDetail.get());
            return "movieDetail";
        } else {
            return "error";
        }
    }
}
