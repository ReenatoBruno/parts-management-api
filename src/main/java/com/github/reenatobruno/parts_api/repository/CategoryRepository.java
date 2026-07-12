package com.github.reenatobruno.parts_api.repository;

import com.github.reenatobruno.parts_api.entity.CategoryEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    boolean existsByCategoryName(String categoryName);

    Page<CategoryEntity> findAllByCategoryNameContainsIgnoreCase(String categoryName, Pageable pageable);
}
