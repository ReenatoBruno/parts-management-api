package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.PartRequestDTO;
import com.github.reenatobruno.parts_api.dto.PartResponseDTO;
import com.github.reenatobruno.parts_api.dto.PartUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartService {

    PartResponseDTO create(PartRequestDTO request);

    Page<PartResponseDTO> getAll(Pageable pageable);

    PartResponseDTO getById(Long id);

    PartResponseDTO update(Long id, PartUpdateDTO request);

    void delete(Long id);
}
