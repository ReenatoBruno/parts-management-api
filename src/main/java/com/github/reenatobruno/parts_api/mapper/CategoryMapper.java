package com.github.reenatobruno.parts_api.mapper;

import com.github.reenatobruno.parts_api.dto.CategoryRequestDTO;
import com.github.reenatobruno.parts_api.dto.CategoryResponseDTO;
import com.github.reenatobruno.parts_api.dto.CategoryUpdateDTO;
import com.github.reenatobruno.parts_api.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryEntity toEntity(CategoryRequestDTO dto) {
        return new CategoryEntity(
                dto.categoryName(),
                dto.categoryDescription()
        );
    }

    public CategoryResponseDTO toResponse(CategoryEntity category) {
        return CategoryResponseDTO.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .categoryDescription(category.getCategoryDescription())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .createdBy(category.getCreatedBy())
                .updatedBy(category.getUpdatedBy())
                .build();
    }

    public void updateEntity(CategoryEntity categoryEntity, CategoryUpdateDTO updateDTO) {
        categoryEntity.updateFields(
                updateDTO.categoryName(),
                updateDTO.categoryDescription()
        );
    }
}
