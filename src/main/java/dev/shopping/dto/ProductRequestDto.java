package dev.shopping.dto;

import dev.shopping.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequestDto {
    @NotBlank(message = "상품 이름은 필수로 입력해야 합니다.")
    @Size(max = 50, message = "이름은 50자를 넘길 수 없습니다.")
    private String name;

    private String category;

    @NotNull(message = "상품 재고는 필수로 입력해야 합니다.")
    @Min(value = 0, message = "상품 재고는 0개 이상이어야 합니다.")
    private Integer stockQuantity;

    @NotNull(message = "상품 가격은 필수로 입력해야 합니다.")
    @Min(value = 0, message = "상품 가격은 0원 이상이어야 합니다.")
    private Integer price;

    private String description;

    public Product toEntity() {
        return Product.builder()
                .name(this.name)
                .category(this.category)    // null이면 엔티티의 @Builder.Default 작동
                .description(this.description) // null이면 엔티티의 @Builder.Default 작동
                .stockQuantity(this.stockQuantity)
                .price(this.price)
                .build();
    }
}

