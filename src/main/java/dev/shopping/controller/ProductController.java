package dev.shopping.controller;

import dev.shopping.dto.ProductRequestDto;
import dev.shopping.entity.Product;
import dev.shopping.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "상품 관리 기능", description = "상품 등록 및 조회 API 목록")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "인증된 사용자가 새로운 상품을 등록합니다.")
    @PostMapping
    public ResponseEntity<EntityModel<Product>> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {

        // 1. 상품 저장
        Product savedProduct = productService.saveProduct(productRequestDto);

        // 2. HATEOAS 링크 생성 (self, all-products)
        WebMvcLinkBuilder selfLinkBuilder = linkTo(methodOn(ProductController.class).getProduct(savedProduct.getId()));
        URI createdUri = selfLinkBuilder.toUri();

        EntityModel<Product> entityModel = EntityModel.of(savedProduct);
        entityModel.add(selfLinkBuilder.withSelfRel());
        entityModel.add(linkTo(methodOn(ProductController.class).getProducts(null, null, null)).withRel("all-products"));

        return ResponseEntity.created(createdUri).body(entityModel);
    }

    /**
     * 상품 전체 목록 조회
     */

    @Operation(summary = "상품 목록 조회", description = "카테고리 필터링과 페이징 처리가 포함된 상품 목록을 조회합니다.\n상품 조회는 모든 사용자가 가능합니다.")
    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestParam(required = false) String category,
            @ParameterObject @PageableDefault(size = 2) Pageable pageable,
            PagedResourcesAssembler<Product> assembler
    ) {
        // 1. 서비스에서 페이징 데이터 가져오기
        Page<Product> productPage = productService.findProducts(category, pageable);

        // 2. assembler를 사용하여 PagedModel 생성
        // toModel의 두 번째 인자는 각 아이템(Product)에 self 링크를 붙여주는 역할을 합니다.
        PagedModel<EntityModel<Product>> pagedModel = assembler.toModel(productPage,
                product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel()));

        // 3. 요구사항: profile 링크 추가 (API 문서 주소)
        pagedModel.add(Link.of("/swagger-ui/index.html").withRel("profile"));

        // 4. self 링크 추가 (현재 페이지의 전체 정보를 담은 주소)
        pagedModel.add(linkTo(methodOn(ProductController.class).getProducts(category, pageable, assembler)).withSelfRel());

        return ResponseEntity.ok(pagedModel);
    }

    // --- (이전의 POST 메서드와 수정/삭제용 빈 메서드들) ---
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Object request) { return null; }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) { return null; }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.findById(id);
        return null;
    }
}