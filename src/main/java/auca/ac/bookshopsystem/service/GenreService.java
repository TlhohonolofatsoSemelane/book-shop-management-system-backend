package auca.ac.bookshopsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.bookshopsystem.model.Genre;
import auca.ac.bookshopsystem.repository.GenreRepository;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public String saveGenre(Genre genre) {
        genreRepository.save(genre);
        return "Genre saved successfully";
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> getGenreById(UUID id) {
        return genreRepository.findById(id);
    }

    public String updateGenre(UUID id, Genre updatedGenre) {
        Optional<Genre> existingGenre = genreRepository.findById(id);

        if (existingGenre.isEmpty()) {
            return "The genre with that id does not exist";
        }

        Genre genre = existingGenre.get();
        genre.setName(updatedGenre.getName());
        genreRepository.save(genre);
        return "Genre updated successfully";
    }

    public String deleteGenre(UUID id) {
        if (!genreRepository.existsById(id)) {
            return "The genre with that id does not exist";
        }
        genreRepository.deleteById(id);
        return "Genre deleted successfully";
    }

    public List<Genre> getUnusedGenres() {
        return genreRepository.findUnusedGenres();
    }
}
