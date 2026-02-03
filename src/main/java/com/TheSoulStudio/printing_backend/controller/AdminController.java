package com.TheSoulStudio.printing_backend.controller;

import com.TheSoulStudio.printing_backend.DTO.AdminRequest;
import com.TheSoulStudio.printing_backend.config.JwtUtil;
import com.TheSoulStudio.printing_backend.entity.*;
import com.TheSoulStudio.printing_backend.repository.AdminRepository;
import com.TheSoulStudio.printing_backend.repository.CategoryRepository;
import com.TheSoulStudio.printing_backend.repository.EnquiryRepository;
import com.TheSoulStudio.printing_backend.repository.ProductRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminRepository adminRepo;
    private final ProductRepository productRepo;
    private final EnquiryRepository enquiryRepo;
    private final JwtUtil jwtUtil;
    private final CategoryRepository categoryRepo;
    private final PasswordEncoder passwordEncoder;

    public AdminController(AdminRepository adminRepo, ProductRepository productRepo, EnquiryRepository enquiryRepo, JwtUtil jwtUtil, CategoryRepository categoryRepo, PasswordEncoder passwordEncoder) {
        this.adminRepo = adminRepo;
        this.productRepo = productRepo;
        this.enquiryRepo = enquiryRepo;
        this.jwtUtil = jwtUtil;
        this.categoryRepo = categoryRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product p){
        Category category = categoryRepo.findById(
                p.getCategory().getId()
        ).orElseThrow(() -> new RuntimeException("Category Not found"));

        p.setCategory(category);
        return productRepo.save(p);

    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@RequestParam Long id,@RequestBody Product p){
        Product existing = productRepo.findById(id).orElseThrow();
        existing.setName(p.getName());
        existing.setDescription(p.getDescription());
        existing.setImageUrl(p.getImageUrl());
        existing.setPrice(p.getPrice());
        existing.setCategory(p.getCategory());
        return productRepo.save(existing);

    }

    @DeleteMapping("products/{id}")
    public void deleteProduct(@RequestParam Long id){
        productRepo.deleteById(id);
    }


    @PostMapping("/login")
    public String login(@RequestBody AdminRequest request){
        Admin admin = adminRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if(! passwordEncoder.matches(request.getPassword(),admin.getPassword())){
            throw new RuntimeException("Invalid Password");
        }

        return jwtUtil.generateToken(admin.getUsername());
    }

    @GetMapping("/enquiries")
    public List<Enquiry> getAllEnquiries(){
        return enquiryRepo.findAll();
    }

    @PutMapping("/enquiries/{id}/status")
    public Enquiry updateStatus(@PathVariable Long id, @RequestParam EnquiryStatus status){
        Enquiry enquiry = enquiryRepo.findById(id)
                .orElseThrow( () -> new RuntimeException("Enquiry not found"));
        enquiry.setStatus(status);
        return enquiryRepo.save(enquiry);
    }
}
