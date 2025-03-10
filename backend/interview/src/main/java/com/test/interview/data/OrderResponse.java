package com.test.interview.data;

import com.test.interview.models.OrderStatus;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private Date orderDate;
    private String shippingAddress;
    private OrderStatus status;
    private List<OrderDetailResponse> details;
}
