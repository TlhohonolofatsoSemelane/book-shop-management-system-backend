package auca.ac.bookshopsystem.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.bookshopsystem.model.Bookseller;
import auca.ac.bookshopsystem.service.BooksellerService;

@RestController
@RequestMapping(value = "/api/booksellers")
public class BooksellerController {

    @Autowired
    private BooksellerService booksellerService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveBookseller(@RequestBody Bookseller bookseller) {
        String returnedMessage = booksellerService.saveBookseller(bookseller);
        return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Bookseller>> getAllBooksellers() {
        return new ResponseEntity<>(booksellerService.getAllBooksellers(), HttpStatus.OK);
    }

    @GetMapping(value = "/by-genre")
    public ResponseEntity<List<Bookseller>> getBooksellersByGenre(@RequestParam String name) {
        return new ResponseEntity<>(booksellerService.getBooksellersByGenre(name), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getBooksellerById(@PathVariable UUID id) {
        Optional<Bookseller> bookseller = booksellerService.getBooksellerById(id);

        if (bookseller.isPresent()) {
            return new ResponseEntity<>(bookseller.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The bookseller with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateBookseller(@PathVariable UUID id, @RequestBody Bookseller bookseller) {
        String returnedMessage = booksellerService.updateBookseller(id, bookseller);

        if (returnedMessage.equals("Bookseller updated successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteBookseller(@PathVariable UUID id) {
        String returnedMessage = booksellerService.deleteBookseller(id);

        if (returnedMessage.equals("Bookseller deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }
}
