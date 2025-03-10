package com.test.interview.data;


import lombok.Data;

@Data
public class ShoppingCartResponse {
    private Long id;
    private String productName;
    private Integer quantity;
    private Double price;
}