package com.github.reenatobruno.parts_api.mapper;

import com.github.reenatobruno.parts_api.dto.PartRequestDTO;
import com.github.reenatobruno.parts_api.dto.PartResponseDTO;
import com.github.reenatobruno.parts_api.entity.Part;
import org.springframework.stereotype.Component;

@Component
public class PartMapper {
    public Part toEntity(PartRequestDTO dto) {
        return new Part(
                dto.getPartNumber(),
                dto.getName(),
                dto.getPrice(),
                dto.getQuantity(),
                dto.getSupplier(),
                dto.getDescription()
        );
    }

public PartResponseDTO toResponseDTO(Part part) {
    return PartResponseDTO.builder()
            .id(part.getId())
            .partNumber(part.getPartNumber())
            .name(part.getName())
            .price(part.getPrice())
            .quantity(part.getQuantity())
            .supplier(part.getSupplier())
            .description(part.getDescription())
            .createdAt(part.getCreatedAt())
            .updatedAt(part.getUpdatedAt())
            .build();
    }
}