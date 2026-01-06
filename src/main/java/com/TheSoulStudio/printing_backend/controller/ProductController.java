package com.TheSoulStudio.printing_backend.controller;

import com.TheSoulStudio.printing_backend.entity.Product;
import com.TheSoulStudio.printing_backend.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Product> getAll(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id){
        return repo.findById(id).orElseThrow();
    }
}
