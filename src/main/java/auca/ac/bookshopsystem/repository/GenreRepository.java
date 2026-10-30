package auca.ac.bookshopsystem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import auca.ac.bookshopsystem.model.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {

    @Query("SELECT g FROM Genre g WHERE g.booksellers IS EMPTY")
    List<Genre> findUnusedGenres();
}
