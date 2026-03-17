package dev.shopping.controller;

import dev.shopping.dto.ProductRequestDto;
import dev.shopping.entity.Product;
import dev.shopping.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<EntityModel<Product>> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {

        // 1. 상품 저장
        Product savedProduct = productService.saveProduct(productRequestDto);

        // 2. HATEOAS 링크 생성 (self, all-products)
        WebMvcLinkBuilder selfLinkBuilder = linkTo(methodOn(ProductController.class).getProduct(savedProduct.getId()));
        URI createdUri = selfLinkBuilder.toUri();

        EntityModel<Product> entityModel = EntityModel.of(savedProduct);
        entityModel.add(selfLinkBuilder.withSelfRel());
        entityModel.add(linkTo(methodOn(ProductController.class).getProducts()).withRel("all-products"));

        return ResponseEntity.created(createdUri).body(entityModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {return null;}

    public ResponseEntity<?> getProducts() {return null;}
}