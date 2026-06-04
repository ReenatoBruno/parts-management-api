package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.PartRequestDTO;
import com.github.reenatobruno.parts_api.dto.PartResponseDTO;
import com.github.reenatobruno.parts_api.dto.PartUpdateDTO;
import com.github.reenatobruno.parts_api.entity.Part;
import com.github.reenatobruno.parts_api.infra.PartNotFoundException;
import com.github.reenatobruno.parts_api.infra.PartNumberAlreadyExistsException;
import com.github.reenatobruno.parts_api.infra.ResourceNotFoundException;
import com.github.reenatobruno.parts_api.mapper.PartMapper;
import com.github.reenatobruno.parts_api.repository.PartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PartServiceImpl implements PartService {

    private final PartRepository repository;
    private final PartMapper mapper;

    public PartServiceImpl(PartRepository repository, PartMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public PartResponseDTO create(PartRequestDTO request) {

        if (repository.existsByPartNumber(request.getPartNumber())) {
            throw new PartNumberAlreadyExistsException(request.getPartNumber());
        }
        Part part = mapper.toEntity(request);

        Part saved = repository.save(part);

        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartResponseDTO> getAll(String partName, Pageable pageable) {

        if (partName == null || partName.isBlank()) {
            return repository.findAll(pageable)
                    .map(mapper::toResponseDTO);
        }
        return repository.findAllByNameContainingIgnoreCase(partName, pageable)
                .map(mapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public PartResponseDTO getById(Long id) {

        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> {
                    log.warn("Part not found with ID: {}", id);

                    return new PartNotFoundException(id);
                });
    }

    @Override
    @Transactional
    public PartResponseDTO update(Long id, PartUpdateDTO request) {

        Part existingPart = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Part not found for update with ID: {}", id);

                    return new PartNotFoundException(id);
                });

        mapper.updateEntity(existingPart, request);

        log.debug("Part updated successfully with ID: {}", id);

        return mapper.toResponseDTO(existingPart);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Part part = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot delete. Part not found with ID: " + id));

        repository.delete(part);
    }
}
