package com.github.reenatobruno.parts_api.mapper;

import com.github.reenatobruno.parts_api.dto.SupplierRequestDTO;
import com.github.reenatobruno.parts_api.dto.SupplierResponseDTO;
import com.github.reenatobruno.parts_api.dto.SupplierUpdateDTO;
import com.github.reenatobruno.parts_api.dto.ViaCepResponseDTO;
import com.github.reenatobruno.parts_api.entity.Address;
import com.github.reenatobruno.parts_api.entity.SupplierEntity;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public SupplierEntity toEntity(SupplierRequestDTO dto, ViaCepResponseDTO viaCep) {

        Address address = new Address(
                dto.zipCode(),
                viaCep.street(),
                dto.number(),
                dto.complement(),
                viaCep.district(),
                viaCep.city(),
                viaCep.state()
        );

        return new SupplierEntity(
                dto.cnpj(),
                dto.supplierName(),
                dto.tradeName(),
                dto.email(),
                dto.phone(),
                address
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
                .zipCode(entity.getAddress().getZipCode())
                .street(entity.getAddress().getStreet())
                .number(entity.getAddress().getNumber())
                .complement(entity.getAddress().getComplement())
                .district(entity.getAddress().getDistrict())
                .city(entity.getAddress().getCity())
                .state(entity.getAddress().getState())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    public void updateEntity(SupplierEntity entity, SupplierUpdateDTO updateDTO, ViaCepResponseDTO viaCep) {

        Address address = null;
        if (viaCep != null) {
            address = new Address(
                    updateDTO.zipCode(),
                    viaCep.street(),
                    updateDTO.number(),
                    updateDTO.complement(),
                    viaCep.district(),
                    viaCep.city(),
                    viaCep.state()
            );
        }

        entity.updateFields(
                updateDTO.supplierName(),
                updateDTO.tradeName(),
                updateDTO.email(),
                updateDTO.phone(),
                address
        );
    }
}
