package auca.ac.bookshopsystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.bookshopsystem.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
}
