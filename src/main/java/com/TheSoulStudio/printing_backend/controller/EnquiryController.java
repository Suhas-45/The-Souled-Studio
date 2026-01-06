package com.TheSoulStudio.printing_backend.controller;

import com.TheSoulStudio.printing_backend.DTO.EnquiryRequest;
import com.TheSoulStudio.printing_backend.entity.Enquiry;
import com.TheSoulStudio.printing_backend.entity.Product;
import com.TheSoulStudio.printing_backend.repository.EnquiryRepository;
import com.TheSoulStudio.printing_backend.repository.ProductRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enquiries")
public class EnquiryController {

    private EnquiryRepository enquiryRepo;
    private ProductRepository productRepo;

    public EnquiryController(ProductRepository productRepo, EnquiryRepository enquiryRepo) {
        this.productRepo = productRepo;
        this.enquiryRepo = enquiryRepo;
    }

    @PostMapping
    public Enquiry create(@RequestBody EnquiryRequest request){
        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
        Enquiry e = new Enquiry();
        e.setCustomerName(request.getCustomerName());
        e.setPhone(request.getPhone());
        e.setEmail(request.getEmail());
        e.setMessage(request.getMessage());
        e.setProduct(product);

        return enquiryRepo.save(e);

    }
}
