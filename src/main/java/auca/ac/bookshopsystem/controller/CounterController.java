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
import org.springframework.web.bind.annotation.RestController;

import auca.ac.bookshopsystem.model.Counter;
import auca.ac.bookshopsystem.service.CounterService;

@RestController
@RequestMapping(value = "/api/counters")
public class CounterController {

    @Autowired
    private CounterService counterService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveCounter(@RequestBody Counter counter) {
        String returnedMessage = counterService.saveCounter(counter);
        return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Counter>> getAllCounters() {
        return new ResponseEntity<>(counterService.getAllCounters(), HttpStatus.OK);
    }

    @GetMapping(value = "/free")
    public ResponseEntity<List<Counter>> getFreeCounters() {
        return new ResponseEntity<>(counterService.getFreeCounters(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCounterById(@PathVariable UUID id) {
        Optional<Counter> counter = counterService.getCounterById(id);

        if (counter.isPresent()) {
            return new ResponseEntity<>(counter.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The counter with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateCounter(@PathVariable UUID id, @RequestBody Counter counter) {
        String returnedMessage = counterService.updateCounter(id, counter);

        if (returnedMessage.equals("Counter updated successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteCounter(@PathVariable UUID id) {
        String returnedMessage = counterService.deleteCounter(id);

        if (returnedMessage.equals("Counter deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }
}
