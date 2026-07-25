package com.github.reenatobruno.parts_api.controller;

import com.github.reenatobruno.parts_api.dto.SupplierRequestDTO;

import com.github.reenatobruno.parts_api.dto.SupplierResponseDTO;
import com.github.reenatobruno.parts_api.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    private final SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SupplierResponseDTO> create(@Valid @RequestBody SupplierRequestDTO requestDTO) {

        SupplierResponseDTO responseDTO = service.create(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.supplierId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }
}
