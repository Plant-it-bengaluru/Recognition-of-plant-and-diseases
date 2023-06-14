package com.example.demo.converters;

import com.example.demo.entities.Customer;
import com.example.demo.models.CustomerRequest;
import com.example.demo.models.response.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerConverter {

    public Customer toEntity(CustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        return customer;
    }

    public CustomerResponse toResponse(Customer customer){
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getCustomer_id());
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        return response;
    }

}
