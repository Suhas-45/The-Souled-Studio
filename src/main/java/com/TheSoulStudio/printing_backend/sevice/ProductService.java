package com.TheSoulStudio.printing_backend.sevice;

import com.TheSoulStudio.printing_backend.DTO.ProductRequest;
import com.TheSoulStudio.printing_backend.DTO.ProductResponse;
import com.TheSoulStudio.printing_backend.entity.Category;
import com.TheSoulStudio.printing_backend.entity.Product;
import com.TheSoulStudio.printing_backend.repository.CategoryRepository;
import com.TheSoulStudio.printing_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    public Page<ProductResponse> getAllProducts(Pageable pageable){
        return productRepo.findAll(pageable)
                .map(this::toResponse);
    }

    public ProductResponse getProduct(Long id){
        Product p = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not found"));
        return toResponse(p);
    }

    public Page<ProductResponse> search(String keyword, Pageable pageable){
        return productRepo.findByNameContainingIgnoreCase(keyword,pageable)
                .map(this::toResponse);
    }

    public Page<ProductResponse> byCategory(Long categoryId, Pageable pageable){
        return productRepo.findByCategoryId(categoryId,pageable)
                .map(this::toResponse);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request){
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found..."));

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found..."));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);

        return toResponse(productRepo.save(product));
    }

    public ProductResponse createProduct(ProductRequest request){
        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);

        Product saved = productRepo.save(product);
        return toResponse(saved);
    }

    public void deleteProduct(Long id){
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found.."));
        productRepo.delete(product);
    }


    private ProductResponse toResponse(Product p){
        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .imageUrl(p.getImageUrl())
                .categoryName(
                        p.getCategory() != null ?
                                p.getCategory().getName() :null
                )
                .build();
    }
}
