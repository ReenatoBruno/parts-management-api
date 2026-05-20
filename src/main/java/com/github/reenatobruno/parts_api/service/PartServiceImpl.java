package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.PartRequestDTO;
import com.github.reenatobruno.parts_api.dto.PartResponseDTO;
import com.github.reenatobruno.parts_api.entity.Part;
import com.github.reenatobruno.parts_api.infra.ResourceNotFoundException;
import com.github.reenatobruno.parts_api.mapper.PartMapper;
import com.github.reenatobruno.parts_api.repository.PartRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartServiceImpl implements PartService{

    private final PartRepository repository;
    private final PartMapper mapper;

    public PartServiceImpl(PartRepository repository, PartMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public PartResponseDTO create(PartRequestDTO request) {
        Part part = mapper.toEntity(request);
        Part saved = repository.save(part);
        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartResponseDTO> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public PartResponseDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException(STR."Part not found with ID: \{id}"));
    }

    @Override
    @Transactional
    public PartResponseDTO update(Long id, PartRequestDTO request) {
        Part existingPart = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(STR."Cannot update. Part not found with ID: \{id}"));

        existingPart.updateFields(
                request.getPartNumber(),
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getSupplier(),
                request.getSupplier()
        );

        Part updated = repository.save(existingPart);

        return mapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(STR."Cannot delete. Part not found with ID: \{id}");
        }
        repository.deleteById(id);
    }
}
