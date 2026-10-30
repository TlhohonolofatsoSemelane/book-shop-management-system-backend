package auca.ac.bookshopsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.bookshopsystem.model.Bookseller;
import auca.ac.bookshopsystem.model.Counter;
import auca.ac.bookshopsystem.model.Genre;
import auca.ac.bookshopsystem.repository.BooksellerRepository;
import auca.ac.bookshopsystem.repository.CounterRepository;
import auca.ac.bookshopsystem.repository.GenreRepository;

@Service
public class BooksellerService {

    @Autowired
    private BooksellerRepository booksellerRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private CounterRepository counterRepository;

    public String saveBookseller(Bookseller bookseller) {
        booksellerRepository.save(bookseller);
        return "Bookseller saved successfully";
    }

    public List<Bookseller> getAllBooksellers() {
        return booksellerRepository.findAll();
    }

    public Optional<Bookseller> getBooksellerById(UUID id) {
        return booksellerRepository.findById(id);
    }

    public String updateBookseller(UUID id, Bookseller updatedBookseller) {
        Optional<Bookseller> existingBookseller = booksellerRepository.findById(id);

        if (existingBookseller.isEmpty()) {
            return "The bookseller with that id does not exist";
        }

        Bookseller bookseller = existingBookseller.get();
        bookseller.setFirstName(updatedBookseller.getFirstName());
        bookseller.setLastName(updatedBookseller.getLastName());
        bookseller.setPhone(updatedBookseller.getPhone());
        booksellerRepository.save(bookseller);
        return "Bookseller updated successfully";
    }

    public String deleteBookseller(UUID id) {
        if (!booksellerRepository.existsById(id)) {
            return "The bookseller with that id does not exist";
        }
        booksellerRepository.deleteById(id);
        return "Bookseller deleted successfully";
    }

    public List<Bookseller> getBooksellersByGenre(String name) {
        return booksellerRepository.findBooksellersByGenreName(name);
    }

    public String addGenreToBookseller(UUID booksellerId, UUID genreId) {
        Optional<Bookseller> booksellerOptional = booksellerRepository.findById(booksellerId);
        Optional<Genre> genreOptional = genreRepository.findById(genreId);

        if (booksellerOptional.isEmpty()) {
            return "The bookseller with that id does not exist";
        }
        if (genreOptional.isEmpty()) {
            return "That genre does not exist";
        }

        Bookseller bookseller = booksellerOptional.get();
        Genre genre = genreOptional.get();

        if (bookseller.getGenres().contains(genre)) {
            return "The bookseller already has that genre";
        }

        bookseller.getGenres().add(genre);
        booksellerRepository.save(bookseller);
        return "Genre added to the bookseller";
    }

    public String changeBooksellerCounter(UUID booksellerId, int counterNumber) {
        Optional<Bookseller> booksellerOptional = booksellerRepository.findById(booksellerId);

        if (booksellerOptional.isEmpty()) {
            return "The bookseller with that id does not exist";
        }

        Optional<Counter> counterOptional = counterRepository.findByCounterNumber(counterNumber);

        if (counterOptional.isEmpty()) {
            return "The counter with that counterNumber does not exist";
        }

        Counter counter = counterOptional.get();

        if (booksellerRepository.existsByCounterAndIdNot(counter, booksellerId)) {
            return "That counter is taken by another bookseller";
        }

        Bookseller bookseller = booksellerOptional.get();
        bookseller.setCounter(counter);
        booksellerRepository.save(bookseller);
        return "Counter changed successfully";
    }
}
