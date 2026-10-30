package auca.ac.bookshopsystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
public class Bookseller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "counter_id")
    private Counter counter;

    @ManyToMany
    @JoinTable(
        name = "bookseller_genre",
        joinColumns = @JoinColumn(name = "bookseller_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();

    public Bookseller() {}

    public UUID getId() { return id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Counter getCounter() { return counter; }
    public void setCounter(Counter counter) { this.counter = counter; }
    public List<Genre> getGenres() { return genres; }
    public void setGenres(List<Genre> genres) { this.genres = genres; }
}
