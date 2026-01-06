package com.TheSoulStudio.printing_backend.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;
    private Double price;
    private String imageUrl;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

}
