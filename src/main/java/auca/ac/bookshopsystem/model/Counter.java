package auca.ac.bookshopsystem.model;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private int counterNumber;

    private String name;

    public Counter() {}

    public Counter(int counterNumber, String name) {
        this.counterNumber = counterNumber;
        this.name = name;
    }

    public UUID getId() { return id; }
    public int getCounterNumber() { return counterNumber; }
    public void setCounterNumber(int counterNumber) { this.counterNumber = counterNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
