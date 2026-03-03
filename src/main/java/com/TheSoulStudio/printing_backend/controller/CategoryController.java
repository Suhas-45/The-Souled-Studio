package com.TheSoulStudio.printing_backend.controller;

import com.TheSoulStudio.printing_backend.entity.Category;
import com.TheSoulStudio.printing_backend.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    public Category createCategory( @RequestBody Category category){
        return categoryRepository.save(category);
    }

    @GetMapping
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        categoryRepository.deleteById(id);
    }
}
