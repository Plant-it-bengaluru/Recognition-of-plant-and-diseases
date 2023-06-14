package com.example.demo.controllers;

import com.example.demo.LogChannel;
import com.example.demo.ThreadLocalVariable;
import com.example.demo.converters.CustomerConverter;
import com.example.demo.entities.Customer;
import com.example.demo.entities.repositories.CustomerRepository;
import com.example.demo.models.CustomerRequest;
import com.example.demo.models.response.CustomerResponse;
import com.example.demo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerConverter customerConverter;
    private final CustomerRepository customerRepository;

    private final CustomerService customerService;

    @PostMapping("/save")
    public CustomerResponse saveCustomer(@RequestBody @Valid CustomerRequest request){
        ThreadLocalVariable.setSessionId(request.getSessionId());
        Customer customer = customerService.saveCustomer(request);
        CustomerResponse response = customerConverter.toResponse(customer);
        LogChannel.push(response, "Server responded with the following response", ThreadLocalVariable.getSessionId());
        return response;
    }
}
