package auca.ac.bookshopsystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.bookshopsystem.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    boolean existsByCustomerNumber(String customerNumber);
}
