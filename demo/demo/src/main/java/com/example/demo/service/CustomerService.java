package com.example.demo.service;

import com.example.demo.LogChannel;
import com.example.demo.ThreadLocalVariable;
import com.example.demo.converters.CustomerConverter;
import com.example.demo.entities.Customer;
import com.example.demo.entities.repositories.CustomerRepository;
import com.example.demo.models.CustomerRequest;
import com.example.demo.models.response.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerConverter customerConverter;
    private final CustomerRepository customerRepository;


    public Customer saveCustomer(CustomerRequest request){
        LogChannel.push(request, "Save customer called", ThreadLocalVariable.getSessionId());
        Customer customer = customerConverter.toEntity(request);
        customerRepository.save(customer);
        return customer;
    }
}
