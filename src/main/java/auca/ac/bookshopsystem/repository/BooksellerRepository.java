package auca.ac.bookshopsystem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import auca.ac.bookshopsystem.model.Bookseller;

@Repository
public interface BooksellerRepository extends JpaRepository<Bookseller, UUID> {

    @Query("SELECT b FROM Bookseller b JOIN b.genres g WHERE LOWER(g.name) = LOWER(:name)")
    List<Bookseller> findBooksellersByGenreName(@Param("name") String name);
}
