package auca.ac.bookshopsystem.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import auca.ac.bookshopsystem.model.Reservation;
import auca.ac.bookshopsystem.model.ReservationStatus;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByStatusOrderByReservationDateAsc(ReservationStatus status);

    List<Reservation> findByReservationDateBetweenOrderByReservationDateAsc(LocalDate start, LocalDate end);

    boolean existsByBooksellerIdAndReservationDateAndStatusNot(UUID booksellerId, LocalDate reservationDate, ReservationStatus status);

    @Query("SELECT r.status, COUNT(r) FROM Reservation r GROUP BY r.status")
    List<Object[]> countReservationsPerStatus();

    @Modifying
    @Query("UPDATE Reservation r "
         + "SET r.status = auca.ac.bookshopsystem.model.ReservationStatus.CANCELLED "
         + "WHERE r.bookseller.id = :booksellerId "
         + "AND r.reservationDate = :date "
         + "AND r.status <> auca.ac.bookshopsystem.model.ReservationStatus.COLLECTED")
    int cancelBooksellerReservationsOnDay(@Param("booksellerId") UUID booksellerId, @Param("date") LocalDate date);
}
