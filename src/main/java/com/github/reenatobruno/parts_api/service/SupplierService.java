package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.SupplierRequestDTO;
import com.github.reenatobruno.parts_api.dto.SupplierResponseDTO;
import com.github.reenatobruno.parts_api.dto.SupplierUpdateDTO;
import com.github.reenatobruno.parts_api.dto.ViaCepResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface SupplierService {

    SupplierResponseDTO create(SupplierRequestDTO requestDTO);

    SupplierResponseDTO getById(UUID supplierId);

    Page<SupplierResponseDTO> getAll(String companyName, Pageable pageable);

    SupplierResponseDTO update(UUID supplierId, SupplierUpdateDTO updateDTO);

    void delete (UUID supplierId);

}
