package com.example.demo.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

    private int order_id;

    private int customer_id;

    private String date;

    private Float total_amount;
}
