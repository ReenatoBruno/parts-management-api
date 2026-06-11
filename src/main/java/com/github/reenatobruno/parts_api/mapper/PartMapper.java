package com.github.reenatobruno.parts_api.mapper;

import com.github.reenatobruno.parts_api.dto.PartRequestDTO;
import com.github.reenatobruno.parts_api.dto.PartResponseDTO;
import com.github.reenatobruno.parts_api.dto.PartUpdateDTO;
import com.github.reenatobruno.parts_api.entity.PartEntity;
import org.springframework.stereotype.Component;

@Component
public class PartMapper {
    public PartEntity toEntity(PartRequestDTO dto) {
        return new PartEntity(
                dto.getPartNumber(),
                dto.getName(),
                dto.getPrice(),
                dto.getQuantity(),
                dto.getSupplier(),
                dto.getDescription()
        );
    }

    public PartResponseDTO toResponseDTO(PartEntity partEntity) {
    return PartResponseDTO.builder()
            .id(partEntity.getId())
            .partNumber(partEntity.getPartNumber())
            .name(partEntity.getName())
            .price(partEntity.getPrice())
            .quantity(partEntity.getQuantity())
            .supplier(partEntity.getSupplier())
            .description(partEntity.getDescription())
            .createdAt(partEntity.getCreatedAt())
            .updatedAt(partEntity.getUpdatedAt())
            .build();
    }

    public void updateEntity(PartEntity partEntity, PartUpdateDTO dto) {
        partEntity.updateFields(
                dto.getName(),
                dto.getPrice(),
                dto.getQuantity(),
                dto.getSupplier(),
                dto.getDescription()
        );
    }
}