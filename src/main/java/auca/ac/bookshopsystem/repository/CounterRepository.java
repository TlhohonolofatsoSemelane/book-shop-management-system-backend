package auca.ac.bookshopsystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.bookshopsystem.model.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, UUID> {
}
