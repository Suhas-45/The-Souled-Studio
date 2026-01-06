package com.TheSoulStudio.printing_backend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EnquiryRequest {

    @NotBlank(message = "Customer name is required.")
    private String customerName;

    @NotBlank(message = "Customer phone no is required for contact.")
    private String phone;

    @Email(message = "Invalid Email format.")
    private String email;

    @Size(max = 1000, message = "Message is too long.")
    private String message;

    @NotNull(message = "Product id is required.")
    private Long productId;
}
