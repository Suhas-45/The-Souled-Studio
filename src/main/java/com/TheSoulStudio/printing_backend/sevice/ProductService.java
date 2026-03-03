package com.TheSoulStudio.printing_backend.sevice;

import com.TheSoulStudio.printing_backend.DTO.ProductRequest;
import com.TheSoulStudio.printing_backend.entity.Product;
import com.TheSoulStudio.printing_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;

    public Page<ProductRequest> getAllProducts(Pageable pageable){
        return productRepo.findAll(pageable)
                .map(this::toDTO);
    }

    public ProductRequest getProduct(Long id){
        Product p = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not found"));
        return toDTO(p);
    }

    public Page<ProductRequest> search(String keyword, Pageable pageable){
        return productRepo.findByNameContainingIgnoreCase(keyword,pageable)
                .map(this::toDTO);
    }

    public Page<ProductRequest> byCategory(Long categoryId, Pageable pageable){
        return productRepo.findByCategoryId(categoryId,pageable)
                .map(this::toDTO);
    }


    private ProductRequest toDTO(Product p){
        return ProductRequest.builder()
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
