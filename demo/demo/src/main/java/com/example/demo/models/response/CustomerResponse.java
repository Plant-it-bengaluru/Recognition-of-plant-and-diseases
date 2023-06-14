package com.example.demo.models.response;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CustomerResponse {
    private int id;
    private String name;
    private String email;
}
