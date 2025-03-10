package com.test.interview.data;

import com.test.interview.models.PaymentMethod;
import com.test.interview.models.PaymentStatus;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentResponse {
    private Long id;
    private Double amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private Date paymentDate;
}