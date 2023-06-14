package com.example.demo.converters;

import com.example.demo.entities.Orders;
import com.example.demo.entities.repositories.CustomerRepository;
import com.example.demo.models.OrdersRequest;
import com.example.demo.models.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrdersConverter {

    CustomerRepository customerRepository;

    public Orders toEntity(OrdersRequest request){
        Orders order = new Orders();
        order.setCustomer(customerRepository.findByCustomerId(request.getCustomer_id()));
        order.setOrder_id(request.getOrder_id());
        order.setDate(request.getDate());
        order.setTotal_amount(order.getTotal_amount());

        return order;
    }

    public OrderResponse toResponse(Orders order){
        OrderResponse response = new OrderResponse();
        response.setOrder_id(order.getOrder_id());
        response.setDate(order.getDate());
        response.setCustomer_id(order.getCustomer().getCustomer_id());
        response.setTotal_amount(order.getTotal_amount());
        return response;
    }
}
