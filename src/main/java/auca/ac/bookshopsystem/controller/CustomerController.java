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

import auca.ac.bookshopsystem.model.Customer;
import auca.ac.bookshopsystem.service.CustomerService;

@RestController
@RequestMapping(value = "/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer) {
        String returnedMessage = customerService.saveCustomer(customer);

        if (returnedMessage.equals("Customer saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping(value = "/by-last-name")
    public ResponseEntity<List<Customer>> getCustomersByLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(customerService.getCustomersByLastName(lastName), HttpStatus.OK);
    }

    @GetMapping(value = "/of-bookseller/{booksellerId}")
    public ResponseEntity<?> getCustomersOfBookseller(@PathVariable UUID booksellerId) {
        if (!customerService.booksellerExists(booksellerId)) {
            return new ResponseEntity<>("The bookseller with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerService.getCustomersOfBookseller(booksellerId), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable UUID id) {
        Optional<Customer> customer = customerService.getCustomerById(id);

        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The customer with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable UUID id, @RequestBody Customer customer) {
        String returnedMessage = customerService.updateCustomer(id, customer);

        if (returnedMessage.equals("Customer updated successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable UUID id) {
        String returnedMessage = customerService.deleteCustomer(id);

        if (returnedMessage.equals("Customer deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }
}
