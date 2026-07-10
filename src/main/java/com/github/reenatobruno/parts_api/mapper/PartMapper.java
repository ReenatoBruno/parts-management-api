package com.github.reenatobruno.parts_api.mapper;

import com.github.reenatobruno.parts_api.dto.PartRequestDTO;
import com.github.reenatobruno.parts_api.dto.PartResponseDTO;
import com.github.reenatobruno.parts_api.dto.PartUpdateDTO;
import com.github.reenatobruno.parts_api.entity.CategoryEntity;
import com.github.reenatobruno.parts_api.entity.PartEntity;
import org.springframework.stereotype.Component;

@Component
public class PartMapper {
    public PartEntity toEntity(PartRequestDTO dto, CategoryEntity category) {
        return new PartEntity(
                dto.partNumber(),
                dto.partName(),
                dto.price(),
                dto.quantity(),
                dto.supplier(),
                dto.description(),
                category
        );
    }

    public PartResponseDTO toResponseDTO(PartEntity partEntity) {
    return PartResponseDTO.builder()
            .id(partEntity.getId())
            .partNumber(partEntity.getPartNumber())
            .partName(partEntity.getPartName())
            .price(partEntity.getPrice())
            .quantity(partEntity.getQuantity())
            .supplier(partEntity.getSupplier())
            .description(partEntity.getDescription())
            .categoryId(partEntity.getCategory().getCategoryId())
            .categoryName(partEntity.getCategory().getCategoryName())
            .createdAt(partEntity.getCreatedAt())
            .updatedAt(partEntity.getUpdatedAt())
            .createdBy(partEntity.getCreatedBy())
            .updatedBy(partEntity.getUpdatedBy())
            .build();
    }

    public void updateEntity(PartEntity partEntity, PartUpdateDTO dto) {
        partEntity.updateFields(
                dto.partName(),
                dto.price(),
                dto.quantity(),
                dto.supplier(),
                dto.description()
        );
    }
}