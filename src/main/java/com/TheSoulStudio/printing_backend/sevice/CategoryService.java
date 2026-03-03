package com.TheSoulStudio.printing_backend.sevice;

import com.TheSoulStudio.printing_backend.DTO.CategoryRequest;
import com.TheSoulStudio.printing_backend.DTO.CategoryResponse;
import com.TheSoulStudio.printing_backend.entity.Category;
import com.TheSoulStudio.printing_backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepo;

    public List<CategoryResponse> getAll(){
        return categoryRepo.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoryResponse create(CategoryRequest request){
        if (categoryRepo.existsByNameIgnoreCase(request.getName())){
            throw new RuntimeException("Category already exists...");
        }

        Category category = new Category();
        category.setName(request.getName());
        Category saved = categoryRepo.save(category);
        return toResponse(saved);
    }

    public CategoryResponse update(Long id, CategoryRequest request){
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(request.getName());
        return toResponse(categoryRepo.save(category));

    }

    public void delete(Long id){
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepo.delete(category);
    }

    private CategoryResponse toResponse(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }


}
