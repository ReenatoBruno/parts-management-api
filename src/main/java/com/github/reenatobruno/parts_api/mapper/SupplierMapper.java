package com.github.reenatobruno.parts_api.mapper;

import com.github.reenatobruno.parts_api.dto.SupplierRequestDTO;
import com.github.reenatobruno.parts_api.dto.SupplierResponseDTO;
import com.github.reenatobruno.parts_api.dto.SupplierUpdateDTO;
import com.github.reenatobruno.parts_api.dto.ViaCepResponseDTO;
import com.github.reenatobruno.parts_api.entity.SupplierEntity;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public SupplierEntity toEntity(SupplierRequestDTO dto, ViaCepResponseDTO address) {
        return new SupplierEntity(
                dto.cnpj(),
                dto.supplierName(),
                dto.tradeName(),
                dto.email(),
                dto.phone(),
                dto.zipCode(),
                address.street(),
                dto.number(),
                dto.complement(),
                address.district(),
                address.city(),
                address.state()
        );
    }

    public SupplierResponseDTO toResponse(SupplierEntity entity) {
        return SupplierResponseDTO.builder()
                .supplierId(entity.getSupplierId())
                .cnpj(entity.getCnpj())
                .supplierName(entity.getSupplierName())
                .tradeName(entity.getTradeName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .zipCode(entity.getZipCode())
                .street(entity.getStreet())
                .number(entity.getNumber())
                .complement(entity.getComplement())
                .district(entity.getDistrict())
                .city(entity.getCity())
                .state(entity.getState())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    public void updateEntity(SupplierEntity entity, SupplierUpdateDTO updateDTO, ViaCepResponseDTO address) {
        entity.updateFields(
                updateDTO.supplierName(),
                updateDTO.tradeName(),
                updateDTO.email(),
                updateDTO.phone(),
                updateDTO.zipCode(),
                address != null ? address.street() : null,
                updateDTO.number(),
                updateDTO.complement(),
                address != null ? address.district() : null,
                address != null ? address.city() : null,
                address != null ? address.state() : null
        );
    }
}
