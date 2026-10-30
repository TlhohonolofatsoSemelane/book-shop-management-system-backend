package auca.ac.bookshopsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.bookshopsystem.model.Bookseller;
import auca.ac.bookshopsystem.repository.BooksellerRepository;

@Service
public class BooksellerService {

    @Autowired
    private BooksellerRepository booksellerRepository;

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
}
