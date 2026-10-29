package auca.ac.bookshopsystem.model;

import java.time.LocalDate;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private LocalDate reservationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.RESERVED;

    private String note;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bookseller_id", nullable = false)
    private Bookseller bookseller;

    public Reservation() {}

    public Reservation(LocalDate reservationDate, String note, Customer customer, Bookseller bookseller) {
        this.reservationDate = reservationDate;
        this.note = note;
        this.customer = customer;
        this.bookseller = bookseller;
    }

    public UUID getId() { return id; }
    public LocalDate getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }
    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Bookseller getBookseller() { return bookseller; }
    public void setBookseller(Bookseller bookseller) { this.bookseller = bookseller; }
}
