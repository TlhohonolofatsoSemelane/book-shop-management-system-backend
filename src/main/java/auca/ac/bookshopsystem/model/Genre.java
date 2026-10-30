package auca.ac.bookshopsystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "genres")
    private List<Bookseller> booksellers = new ArrayList<>();

    public Genre() {}

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    @JsonIgnore
    public List<Bookseller> getBooksellers() { return booksellers; }
}
