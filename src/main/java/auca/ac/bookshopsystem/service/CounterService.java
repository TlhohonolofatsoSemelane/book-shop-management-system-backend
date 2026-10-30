package auca.ac.bookshopsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.bookshopsystem.model.Counter;
import auca.ac.bookshopsystem.repository.CounterRepository;

@Service
public class CounterService {

    @Autowired
    private CounterRepository counterRepository;

    public String saveCounter(Counter counter) {
        counterRepository.save(counter);
        return "Counter saved successfully";
    }

    public List<Counter> getAllCounters() {
        return counterRepository.findAll();
    }

    public Optional<Counter> getCounterById(UUID id) {
        return counterRepository.findById(id);
    }

    public String updateCounter(UUID id, Counter updatedCounter) {
        Optional<Counter> existingCounter = counterRepository.findById(id);

        if (existingCounter.isEmpty()) {
            return "The counter with that id does not exist";
        }

        Counter counter = existingCounter.get();
        counter.setCounterNumber(updatedCounter.getCounterNumber());
        counter.setName(updatedCounter.getName());
        counterRepository.save(counter);
        return "Counter updated successfully";
    }

    public String deleteCounter(UUID id) {
        if (!counterRepository.existsById(id)) {
            return "The counter with that id does not exist";
        }
        counterRepository.deleteById(id);
        return "Counter deleted successfully";
    }

    public List<Counter> getFreeCounters() {
        return counterRepository.findFreeCounters();
    }
}
