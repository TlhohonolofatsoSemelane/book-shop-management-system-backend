package auca.ac.bookshopsystem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import auca.ac.bookshopsystem.model.Purchase;
import auca.ac.bookshopsystem.model.Reservation;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {

    boolean existsByReservation(Reservation reservation);

    @Query("SELECT p FROM Purchase p "
         + "WHERE p.reservation.customer.customerNumber = :customerNumber "
         + "ORDER BY p.purchaseDate DESC")
    List<Purchase> findPurchasesOfCustomer(@Param("customerNumber") String customerNumber);
}
