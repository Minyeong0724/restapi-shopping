package dev.shopping.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private Integer price;

    @Builder.Default
    private String category = "기타"; // 기본값 설정

    @Builder.Default
    private String description = "상품 소개 준비중..."; // 기본값 설정

}
