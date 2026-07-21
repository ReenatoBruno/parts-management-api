package com.github.reenatobruno.parts_api.mapper;

import com.github.reenatobruno.parts_api.dto.*;
import com.github.reenatobruno.parts_api.entity.DealershipEntity;
import org.springframework.stereotype.Component;

@Component
public class DealershipMapper {

    public DealershipEntity toEntity(DealershipRequestDTO dto, ViaCepResponseDTO address) {
        return new DealershipEntity(
                dto.cnpj(),
                dto.dealerName(),
                dto.tradeName(),
                dto.email(),
                dto.phone(),
                dto.zip(),
                address.street(),
                dto.number(),
                dto.complement(),
                address.district(),
                address.city(),
                address.state()
        );
    }

    public DealershipAdminResponseDTO toAdminResponse(DealershipEntity entity) {
        return new DealershipAdminResponseDTO(
                toResponse(entity),
                entity.getCnpj(),
                entity.getZipCode(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getComplement(),
                entity.getDistrict(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedBy()
        );
    }

    public DealershipResponseDTO toResponse(DealershipEntity entity) {
        return DealershipResponseDTO.builder()
                .dealerId(entity.getDealerId())
                .dealerName(entity.getDealerName())
                .tradeName(entity.getTradeName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .city(entity.getCity())
                .state(entity.getState())
                .build();
    }

    public void updateFields(DealershipEntity entity, DealershipUpdateDTO updateDTO, ViaCepResponseDTO address) {
        entity.updateFields(
                updateDTO.dealerName(),
                updateDTO.tradeName(),
                updateDTO.email(),
                updateDTO.phone(),
                updateDTO.zip(),
                address != null ? address.street() : null,
                updateDTO.number(),
                updateDTO.complement(),
                address != null ? address.district() : null,
                address != null ? address.city() : null,
                address != null ? address.state() : null
        );
    }
}
