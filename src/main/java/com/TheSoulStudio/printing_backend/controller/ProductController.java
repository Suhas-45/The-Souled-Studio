package com.TheSoulStudio.printing_backend.controller;

import com.TheSoulStudio.printing_backend.DTO.ProductRequest;
import com.TheSoulStudio.printing_backend.entity.Product;
import com.TheSoulStudio.printing_backend.repository.ProductRepository;
import com.TheSoulStudio.printing_backend.sevice.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repo;
    private final ProductService service;

    public ProductController(ProductRepository repo, ProductService service) {
        this.repo = repo;
        this.service = service;
    }

    @GetMapping
    public Page<ProductRequest> list(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return service.getAllProducts(
                PageRequest.of(page, size)
        );
    }

    @GetMapping("/{id}")
    public ProductRequest getById(@PathVariable Long id){
        return service.getProduct(id);
    }

    @GetMapping("/search")
    public Page<ProductRequest> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return service.search(keyword,PageRequest.of(page,size));
    }

    @GetMapping("/category/{id}")
    public Page<ProductRequest> byCategory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return service.byCategory(id,PageRequest.of(page,size));
    }






}
