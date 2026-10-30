package auca.ac.bookshopsystem.model;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String customerNumber;

    private String email;

    public Customer() {}

    public UUID getId() { return id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getCustomerNumber() { return customerNumber; }
    public void setCustomerNumber(String customerNumber) { this.customerNumber = customerNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
