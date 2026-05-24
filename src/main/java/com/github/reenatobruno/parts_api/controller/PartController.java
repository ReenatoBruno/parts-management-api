package com.github.reenatobruno.parts_api.controller;

import com.github.reenatobruno.parts_api.dto.PartRequestDTO;
import com.github.reenatobruno.parts_api.dto.PartResponseDTO;
import com.github.reenatobruno.parts_api.dto.PartUpdateDTO;
import com.github.reenatobruno.parts_api.service.PartService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/parts")
public class PartController implements PartControllerOpenApi {

    private final PartService partService;

    public PartController(PartService partService) {

        this.partService = partService;
    }

    @PostMapping
    public ResponseEntity<PartResponseDTO> create(@Valid @RequestBody PartRequestDTO request) {
        PartResponseDTO response = partService.create(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<PartResponseDTO>> getAll(@PageableDefault(size = 20, sort = "id")Pageable pageable) {
        return ResponseEntity.ok(partService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(partService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PartUpdateDTO request) {
        return ResponseEntity.ok(partService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        partService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
