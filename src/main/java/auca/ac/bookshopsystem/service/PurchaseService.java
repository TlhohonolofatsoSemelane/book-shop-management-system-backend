package auca.ac.bookshopsystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.bookshopsystem.model.Purchase;
import auca.ac.bookshopsystem.model.Reservation;
import auca.ac.bookshopsystem.model.ReservationStatus;
import auca.ac.bookshopsystem.repository.PurchaseRepository;
import auca.ac.bookshopsystem.repository.ReservationRepository;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public String savePurchase(UUID reservationId, Purchase purchase) {
        Optional<Reservation> existingReservation = reservationRepository.findById(reservationId);

        if (existingReservation.isEmpty()) {
            return "The reservation with that id does not exist";
        }

        Reservation reservation = existingReservation.get();

        if (reservation.getStatus() != ReservationStatus.COLLECTED) {
            return "Purchases are only for COLLECTED reservations";
        }

        purchase.setPurchaseDate(LocalDate.now());
        purchase.setReservation(reservation);
        purchaseRepository.save(purchase);
        return "Purchase saved successfully";
    }

    public List<Purchase> getPurchasesOfCustomer(String customerNumber) {
        return purchaseRepository.findPurchasesOfCustomer(customerNumber);
    }

    public String deletePurchase(UUID id) {
        if (!purchaseRepository.existsById(id)) {
            return "The purchase with that id does not exist";
        }
        purchaseRepository.deleteById(id);
        return "Purchase deleted successfully";
    }
}
