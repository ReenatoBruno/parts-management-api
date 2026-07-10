package com.github.reenatobruno.parts_api.repository;

import com.github.reenatobruno.parts_api.entity.CategoryEntity;
import com.github.reenatobruno.parts_api.entity.PartEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PartRepository extends JpaRepository<PartEntity, UUID> {

    boolean existsByPartNumber(String partNumber);

    Page<PartEntity> findAllByPartNameContainingIgnoreCase(String partName, Pageable pageable);

    boolean existsByCategory(CategoryEntity category);
}
