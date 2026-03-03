package com.TheSoulStudio.printing_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Enquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customerName;
    private String phone;
    private String email;

    @Column(length = 1000)
    private String message;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private EnquiryStatus status;

}
