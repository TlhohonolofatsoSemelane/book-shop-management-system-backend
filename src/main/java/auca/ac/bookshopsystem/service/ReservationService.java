package auca.ac.bookshopsystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.bookshopsystem.model.Reservation;
import auca.ac.bookshopsystem.model.ReservationStatus;
import auca.ac.bookshopsystem.repository.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public String saveReservation(Reservation reservation) {
        if (reservation.getReservationDate().isBefore(LocalDate.now())) {
            return "Reservation date cannot be in the past";
        }

        boolean alreadyBooked = reservationRepository.existsByBooksellerIdAndReservationDateAndStatusNot(
                reservation.getBookseller().getId(), reservation.getReservationDate(), ReservationStatus.CANCELLED);

        if (alreadyBooked) {
            return "Bookseller already prepares a reservation on that date";
        }

        reservation.setStatus(ReservationStatus.RESERVED);
        reservationRepository.save(reservation);
        return "Reservation saved successfully";
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(UUID id) {
        return reservationRepository.findById(id);
    }

    public String updateReservation(UUID id, Reservation updatedReservation) {
        Optional<Reservation> existingReservation = reservationRepository.findById(id);

        if (existingReservation.isEmpty()) {
            return "The reservation with that id does not exist";
        }

        Reservation reservation = existingReservation.get();
        reservation.setReservationDate(updatedReservation.getReservationDate());
        reservation.setNote(updatedReservation.getNote());
        reservationRepository.save(reservation);
        return "Reservation updated successfully";
    }

    public String deleteReservation(UUID id) {
        if (!reservationRepository.existsById(id)) {
            return "The reservation with that id does not exist";
        }
        reservationRepository.deleteById(id);
        return "Reservation deleted successfully";
    }

    public List<Reservation> getReservationsByStatus(ReservationStatus status) {
        return reservationRepository.findByStatusOrderByReservationDateAsc(status);
    }

    public List<Reservation> getReservationsBetween(LocalDate start, LocalDate end) {
        return reservationRepository.findByReservationDateBetweenOrderByReservationDateAsc(start, end);
    }

    public String changeStatus(UUID id, ReservationStatus newStatus) {
        Optional<Reservation> existingReservation = reservationRepository.findById(id);

        if (existingReservation.isEmpty()) {
            return "The reservation with that id does not exist";
        }

        Reservation reservation = existingReservation.get();
        ReservationStatus currentStatus = reservation.getStatus();
        boolean allowed = false;

        if (currentStatus == ReservationStatus.RESERVED
                && (newStatus == ReservationStatus.READY || newStatus == ReservationStatus.CANCELLED)) {
            allowed = true;
        }
        if (currentStatus == ReservationStatus.READY
                && (newStatus == ReservationStatus.COLLECTED || newStatus == ReservationStatus.CANCELLED)) {
            allowed = true;
        }

        if (!allowed) {
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }

        reservation.setStatus(newStatus);
        reservationRepository.save(reservation);
        return "Reservation status updated successfully";
    }
}
