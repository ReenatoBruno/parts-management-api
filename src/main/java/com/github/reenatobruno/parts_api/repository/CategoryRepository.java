package com.github.reenatobruno.parts_api.repository;

import com.github.reenatobruno.parts_api.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
}
