package dev.shopping.service;

import dev.shopping.dto.ProductRequestDto;
import dev.shopping.entity.Product;
import dev.shopping.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final 필드(productRepository)에 대한 생성자를 자동으로 생성(초기화)
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Product saveProduct(ProductRequestDto dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .build();
        return productRepository.save(product);
    }

}
