package auca.ac.bookshopsystem.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.bookshopsystem.model.Reservation;
import auca.ac.bookshopsystem.model.ReservationStatus;
import auca.ac.bookshopsystem.service.ReservationService;

@RestController
@RequestMapping(value = "/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveReservation(@RequestBody Reservation reservation) {
        String returnedMessage = reservationService.saveReservation(reservation);

        if (returnedMessage.equals("Reservation saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        } else if (returnedMessage.equals("Reservation date cannot be in the past")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return new ResponseEntity<>(reservationService.getAllReservations(), HttpStatus.OK);
    }

    @GetMapping(value = "/by-status")
    public ResponseEntity<List<Reservation>> getReservationsByStatus(@RequestParam ReservationStatus status) {
        return new ResponseEntity<>(reservationService.getReservationsByStatus(status), HttpStatus.OK);
    }

    @GetMapping(value = "/between")
    public ResponseEntity<List<Reservation>> getReservationsBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(reservationService.getReservationsBetween(startDate, endDate), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable UUID id, @RequestParam ReservationStatus status) {
        String returnedMessage = reservationService.changeStatus(id, status);

        if (returnedMessage.equals("Reservation status updated successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else if (returnedMessage.equals("The reservation with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/stats/by-status")
    public ResponseEntity<List<Object[]>> getReservationCountsPerStatus() {
        return new ResponseEntity<>(reservationService.getReservationCountsPerStatus(), HttpStatus.OK);
    }

    @PatchMapping(value = "/cancel-day")
    public ResponseEntity<String> cancelBooksellerDay(@RequestParam UUID booksellerId, @RequestParam String date) {
        LocalDate day = LocalDate.parse(date);
        return new ResponseEntity<>(reservationService.cancelBooksellerDay(booksellerId, day), HttpStatus.OK);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<Reservation>> getReservationsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "reservationDate,desc") String sort) {
        String[] sortParts = sort.split(",");
        String field = sortParts[0];
        Sort.Direction direction = Sort.Direction.ASC;

        if (sortParts.length > 1) {
            direction = Sort.Direction.fromString(sortParts[1]);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));
        return new ResponseEntity<>(reservationService.getReservationsPage(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable UUID id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);

        if (reservation.isPresent()) {
            return new ResponseEntity<>(reservation.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The reservation with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable UUID id, @RequestBody Reservation reservation) {
        String returnedMessage = reservationService.updateReservation(id, reservation);

        if (returnedMessage.equals("Reservation updated successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable UUID id) {
        String returnedMessage = reservationService.deleteReservation(id);

        if (returnedMessage.equals("Reservation deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else if (returnedMessage.equals("That reservation has purchases, delete them first")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }
}
