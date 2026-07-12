package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.PartRequestDTO;
import com.github.reenatobruno.parts_api.dto.PartResponseDTO;
import com.github.reenatobruno.parts_api.dto.PartUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PartService {

    PartResponseDTO create(PartRequestDTO requestDTO);

    PartResponseDTO getById(UUID partId);

    Page<PartResponseDTO> getAll(String partName, Pageable pageable);

    PartResponseDTO update(UUID partId, PartUpdateDTO requestDTO);

    void delete(UUID partId);
}
