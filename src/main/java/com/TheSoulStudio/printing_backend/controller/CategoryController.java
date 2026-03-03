package com.TheSoulStudio.printing_backend.controller;

import com.TheSoulStudio.printing_backend.DTO.CategoryResponse;
import com.TheSoulStudio.printing_backend.sevice.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAll();
    }
}
