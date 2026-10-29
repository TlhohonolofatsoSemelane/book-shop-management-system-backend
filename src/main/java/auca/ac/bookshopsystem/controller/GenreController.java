package auca.ac.bookshopsystem.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.bookshopsystem.model.Genre;
import auca.ac.bookshopsystem.service.GenreService;

@RestController
@RequestMapping(value = "/api/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveGenre(@RequestBody Genre genre) {
        String returnedMessage = genreService.saveGenre(genre);
        return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Genre>> getAllGenres() {
        return new ResponseEntity<>(genreService.getAllGenres(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getGenreById(@PathVariable UUID id) {
        Optional<Genre> genre = genreService.getGenreById(id);

        if (genre.isPresent()) {
            return new ResponseEntity<>(genre.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The genre with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateGenre(@PathVariable UUID id, @RequestBody Genre genre) {
        String returnedMessage = genreService.updateGenre(id, genre);

        if (returnedMessage.equals("Genre updated successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable UUID id) {
        String returnedMessage = genreService.deleteGenre(id);

        if (returnedMessage.equals("Genre deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }
}
