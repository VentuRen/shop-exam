package com.test.interview.data;

import lombok.Data;

@Data
public class OrderDetailResponse {
    private String productName;
    private Integer quantity;
    private Double price;
}