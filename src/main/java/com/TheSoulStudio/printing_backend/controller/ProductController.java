package com.TheSoulStudio.printing_backend.controller;

import com.TheSoulStudio.printing_backend.DTO.ProductResponse;
import com.TheSoulStudio.printing_backend.sevice.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    private final ProductService service;

    public ProductController( ProductService service) {
        this.service = service;
    }

    @GetMapping
    public Page<ProductResponse> list(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return service.getAllProducts(
                PageRequest.of(page, size)
        );
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id){
        return service.getProduct(id);
    }

    @GetMapping("/search")
    public Page<ProductResponse> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return service.search(keyword,PageRequest.of(page,size));
    }

    @GetMapping("/category/{id}")
    public Page<ProductResponse> byCategory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return service.byCategory(id,PageRequest.of(page,size));
    }






}
