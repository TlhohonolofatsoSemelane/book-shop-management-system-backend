package auca.ac.bookshopsystem.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.bookshopsystem.model.ReservationStatus;
import auca.ac.bookshopsystem.repository.BooksellerRepository;
import auca.ac.bookshopsystem.repository.CounterRepository;
import auca.ac.bookshopsystem.repository.CustomerRepository;
import auca.ac.bookshopsystem.repository.PurchaseRepository;
import auca.ac.bookshopsystem.repository.ReservationRepository;

@Service
public class DashboardService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BooksellerRepository booksellerRepository;

    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("customers", customerRepository.count());
        summary.put("booksellers", booksellerRepository.count());
        summary.put("freeCounters", (long) counterRepository.findFreeCounters().size());
        summary.put("reservationsToday", reservationRepository.countByReservationDate(LocalDate.now()));
        summary.put("reserved", reservationRepository.countByStatus(ReservationStatus.RESERVED));
        summary.put("purchases", purchaseRepository.count());
        return summary;
    }
}
