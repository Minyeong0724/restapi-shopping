package dev.shopping.repository;

import dev.shopping.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PagedResourcesAssembler;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory(String category, Pageable pageable);

//    Page<Product> findAll(Pageable pageable, PagedResourcesAssembler<Product> assembler);
}
