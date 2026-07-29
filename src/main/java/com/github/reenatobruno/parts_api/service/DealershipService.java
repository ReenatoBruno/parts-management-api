package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.DealershipRequestDTO;
import com.github.reenatobruno.parts_api.dto.DealershipResponseDTO;
import com.github.reenatobruno.parts_api.dto.DealershipUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DealershipService {

    DealershipResponseDTO create(DealershipRequestDTO requestDTO);

    DealershipResponseDTO getById(UUID dealerId);

    Page<DealershipResponseDTO> getAll(String dealerName, Pageable pageable);

    DealershipResponseDTO update(UUID dealerId, DealershipUpdateDTO updateDTO);

    void delete (UUID dealerId);




}
