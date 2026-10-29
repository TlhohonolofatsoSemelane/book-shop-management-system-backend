package auca.ac.bookshopsystem.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.bookshopsystem.model.Reservation;
import auca.ac.bookshopsystem.model.ReservationStatus;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByStatusOrderByReservationDateAsc(ReservationStatus status);

    List<Reservation> findByReservationDateBetweenOrderByReservationDateAsc(LocalDate start, LocalDate end);
}
