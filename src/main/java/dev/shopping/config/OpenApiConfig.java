package dev.shopping.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Shopping Mall REST API") // 상단 제목
                        .description("상품 등록, 조회, 수정, 삭제 기능을 제공하는 쇼핑몰 실습 API 문서입니다.\n(현재는 등록, 조회만 구현되어 있음)") // 상단 설명
                        .version("v1.0.0"));
    }

    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group("상품 페이지")
                .pathsToMatch("/api/products/**")
                .build();
    }

    @Bean
    public GroupedOpenApi allApi() {
        return GroupedOpenApi.builder()
                .group("전체 API")
                .pathsToMatch("/**")
                .build();
    }
}
