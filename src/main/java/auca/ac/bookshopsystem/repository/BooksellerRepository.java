package auca.ac.bookshopsystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.bookshopsystem.model.Bookseller;

@Repository
public interface BooksellerRepository extends JpaRepository<Bookseller, UUID> {
}
