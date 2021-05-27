package com.example.demo.controller;

import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerRepository customerRepository;


    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/all")
    public Iterable<Customer> getCustomers() {

        return customerRepository.findAll();
    }

    @PostMapping("/add")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("/find/{lastName}")
    public Iterable<Customer> findCustByLastname(@PathVariable String lastName) {

        return customerRepository.findByLastName(lastName);
    }


}
