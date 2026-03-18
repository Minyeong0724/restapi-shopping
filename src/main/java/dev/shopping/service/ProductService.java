package dev.shopping.service;

import dev.shopping.dto.ProductRequestDto;
import dev.shopping.entity.Product;
import dev.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * category 값이 null이 아닐 경우 카테고리로 조회
     * cateogry 값이 null 일 경우 모든 상품 조회
     * @param category
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Product> findProducts(String category, Pageable pageable) {
        if (category == null || category.isBlank()) {
            return productRepository.findAll(pageable);
        }
        return productRepository.findByCategory(category, pageable);
    }
}
