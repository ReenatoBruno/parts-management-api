package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.PartRequestDTO;
import com.github.reenatobruno.parts_api.dto.PartResponseDTO;
import com.github.reenatobruno.parts_api.dto.PartUpdateDTO;
import com.github.reenatobruno.parts_api.entity.PartEntity;
import com.github.reenatobruno.parts_api.exception.PartNotFoundException;
import com.github.reenatobruno.parts_api.exception.PartNumberAlreadyExistsException;
import com.github.reenatobruno.parts_api.mapper.PartMapper;
import com.github.reenatobruno.parts_api.repository.PartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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

        log.info("Checking if part number already exists {}", request.getPartNumber());

        if (repository.existsByPartNumber(request.getPartNumber())) {

            log.warn("Part number already exists {}", request.getPartNumber());

            throw new PartNumberAlreadyExistsException(request.getPartNumber());
        }
        PartEntity partEntity = mapper.toEntity(request);

        try {
            PartEntity saved = repository.save(partEntity);

            log.info("Part created successfully with ID: {} and Part Number: {}", saved.getId(), saved.getPartNumber());

            return mapper.toResponseDTO(saved);

        } catch (DataIntegrityViolationException e) {

            log.error("Database integrity violation while creating part: {}", request.getPartNumber());

            throw new PartNumberAlreadyExistsException(request.getPartNumber());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PartResponseDTO getById(Long id) {

        log.info("Fetching part with ID: {}", id);

        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> {
                    log.warn("Part not found with ID: {}", id);

                    return new PartNotFoundException(id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartResponseDTO> getAll(String partName, Pageable pageable) {

        log.debug("Fetching all parts with filter part name {}", partName);

        if (partName == null || partName.isBlank()) {
            return repository.findAll(pageable)
                    .map(mapper::toResponseDTO);
        }
        return repository.findAllByNameContainingIgnoreCase(partName, pageable)
                .map(mapper::toResponseDTO);
    }

    @Override
    @Transactional
    public PartResponseDTO update(Long id, PartUpdateDTO request) {

        log.info("Updating part with ID: {}", id);

        PartEntity existingPartEntity = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Part not found for update with ID: {}", id);

                    return new PartNotFoundException(id);
                });

        mapper.updateEntity(existingPartEntity, request);

        PartEntity updated = repository.save(existingPartEntity);

        log.info("Part updated successfully with ID: {}", id);

        return mapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        log.info("Deleting part with ID: {}", id);

        PartEntity partEntity = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Part not found for deletion with ID: {}", id);

                    return new PartNotFoundException(id);
                });

        repository.delete(partEntity);

        log.info("Part deleted successfully with ID: {}", id);
    }
}
