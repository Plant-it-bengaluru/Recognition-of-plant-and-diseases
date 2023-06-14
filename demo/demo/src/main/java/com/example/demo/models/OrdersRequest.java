package com.example.demo.models;


import com.example.demo.entities.Customer;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrdersRequest {

    private int order_id;

    @NotNull
    @NotEmpty
    private int customer_id;

    @NotNull
    @NotEmpty
    private String date;

    @NotNull
    @NotEmpty
    private Float total_amount;
}
