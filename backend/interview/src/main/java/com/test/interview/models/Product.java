package com.test.interview.models;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Integer stock;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
}