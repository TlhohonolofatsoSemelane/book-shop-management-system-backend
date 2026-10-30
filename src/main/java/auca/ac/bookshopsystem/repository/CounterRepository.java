package auca.ac.bookshopsystem.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import auca.ac.bookshopsystem.model.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, UUID> {

    @Query("SELECT c FROM Counter c "
         + "WHERE NOT EXISTS (SELECT b FROM Bookseller b WHERE b.counter = c) "
         + "ORDER BY c.counterNumber ASC")
    List<Counter> findFreeCounters();

    Optional<Counter> findByCounterNumber(int counterNumber);
}
