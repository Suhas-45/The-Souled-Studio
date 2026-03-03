package com.TheSoulStudio.printing_backend.controller;

import com.TheSoulStudio.printing_backend.DTO.AdminRequest;
import com.TheSoulStudio.printing_backend.DTO.ProductRequest;
import com.TheSoulStudio.printing_backend.DTO.ProductResponse;
import com.TheSoulStudio.printing_backend.config.JwtUtil;
import com.TheSoulStudio.printing_backend.entity.*;
import com.TheSoulStudio.printing_backend.repository.AdminRepository;
import com.TheSoulStudio.printing_backend.repository.CategoryRepository;
import com.TheSoulStudio.printing_backend.repository.EnquiryRepository;
import com.TheSoulStudio.printing_backend.repository.ProductRepository;
import com.TheSoulStudio.printing_backend.sevice.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminRepository adminRepo;
    private final ProductRepository productRepo;
    private final EnquiryRepository enquiryRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final ProductService productService;

    public AdminController(AdminRepository adminRepo, ProductRepository productRepo, EnquiryRepository enquiryRepo, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, ProductService productService) {
        this.adminRepo = adminRepo;
        this.productRepo = productRepo;
        this.enquiryRepo = enquiryRepo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.productService = productService;
    }

    @PostMapping("/products")
    public ProductResponse createProduct( @Valid @RequestBody ProductRequest request){
        return productService.createProduct(request);
    }

    @PutMapping("/products/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request){
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("products/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
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

    @GetMapping("/dashboard")
    public Map<String,Long> dashboard(){
        Map<String, Long> data = new HashMap<>();

        data.put("Total", enquiryRepo.count());
        data.put("New",enquiryRepo.countByStatus(EnquiryStatus.NEW));
        data.put("Contacted",enquiryRepo.countByStatus(EnquiryStatus.CONTACTED));
        data.put("Closed",enquiryRepo.countByStatus(EnquiryStatus.CLOSE));
        return data;
    }
}
