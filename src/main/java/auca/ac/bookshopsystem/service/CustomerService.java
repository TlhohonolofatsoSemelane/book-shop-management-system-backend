package auca.ac.bookshopsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.bookshopsystem.model.Customer;
import auca.ac.bookshopsystem.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public String saveCustomer(Customer customer) {
        if (customerRepository.existsByCustomerNumber(customer.getCustomerNumber())) {
            return "Customer with this customer number already exists";
        }
        customerRepository.save(customer);
        return "Customer saved successfully";
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    public String updateCustomer(UUID id, Customer updatedCustomer) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);

        if (existingCustomer.isEmpty()) {
            return "The customer with that id does not exist";
        }

        Customer customer = existingCustomer.get();
        customer.setFirstName(updatedCustomer.getFirstName());
        customer.setLastName(updatedCustomer.getLastName());
        customer.setCustomerNumber(updatedCustomer.getCustomerNumber());
        customer.setEmail(updatedCustomer.getEmail());
        customerRepository.save(customer);
        return "Customer updated successfully";
    }

    public String deleteCustomer(UUID id) {
        if (!customerRepository.existsById(id)) {
            return "The customer with that id does not exist";
        }
        customerRepository.deleteById(id);
        return "Customer deleted successfully";
    }
}
