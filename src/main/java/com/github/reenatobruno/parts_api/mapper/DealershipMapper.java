package com.github.reenatobruno.parts_api.mapper;

import com.github.reenatobruno.parts_api.dto.*;
import com.github.reenatobruno.parts_api.entity.Address;
import com.github.reenatobruno.parts_api.entity.DealershipEntity;
import org.springframework.stereotype.Component;

@Component
public class DealershipMapper {

    public DealershipEntity toEntity(DealershipRequestDTO dto, ViaCepResponseDTO viaCep) {
        Address address = new Address(
                dto.zipCode(),
                viaCep.street(),
                dto.number(),
                dto.complement(),
                viaCep.district(),
                viaCep.city(),
                viaCep.state()
        );

        return new DealershipEntity(
                dto.cnpj(),
                dto.dealerName(),
                dto.tradeName(),
                dto.email(),
                dto.phone(),
                address

        );
    }

    public DealershipResponseDTO toResponse(DealershipEntity entity) {
        return DealershipResponseDTO.builder()
                .dealerId(entity.getDealerId())
                .dealerName(entity.getDealerName())
                .tradeName(entity.getTradeName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .city(entity.getAddress().getCity())
                .state(entity.getAddress().getState())
                .build();
    }

    public DealershipAdminResponseDTO toAdminResponse(DealershipEntity entity) {
        return new DealershipAdminResponseDTO(
                toResponse(entity),
                entity.getCnpj(),
                entity.getAddress().getZipCode(),
                entity.getAddress().getStreet(),
                entity.getAddress().getNumber(),
                entity.getAddress().getComplement(),
                entity.getAddress().getDistrict(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedBy()
        );
    }

    public void updateEntity(DealershipEntity entity, DealershipUpdateDTO updateDTO, ViaCepResponseDTO viaCep) {
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
                updateDTO.dealerName(),
                updateDTO.tradeName(),
                updateDTO.email(),
                updateDTO.phone(),
                address

        );
    }
}
