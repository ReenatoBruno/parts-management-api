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

import java.util.UUID;

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

        log.info("Checking if part number already exists {}", request.partNumber());

        if (repository.existsByPartNumber(request.partNumber())) {

            log.warn("Part number already exists {}", request.partNumber());

            throw new PartNumberAlreadyExistsException(request.partNumber());
        }
        PartEntity partEntity = mapper.toEntity(request);

        try {
            PartEntity partSaved = repository.save(partEntity);

            log.info("Part created successfully with ID: {} and Part Number: {}", partSaved.getId(), partSaved.getPartNumber());

            return mapper.toResponseDTO(partSaved);

        } catch (DataIntegrityViolationException e) {

            log.error("Database integrity violation while creating part: {}", request.partNumber());

            throw new PartNumberAlreadyExistsException(request.partNumber());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PartResponseDTO getById(UUID partId) {

        log.info("Fetching part with ID: {}", partId);

        return mapper.toResponseDTO(validatePart(partId));
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
    public PartResponseDTO update(UUID partId, PartUpdateDTO request) {

        log.info("Updating part with ID: {}", partId);

        PartEntity existingPartEntity = validatePart(partId);

        mapper.updateEntity(existingPartEntity, request);

        PartEntity partUpdated = repository.save(existingPartEntity);

        log.info("Part updated successfully with ID: {}", partId);

        return mapper.toResponseDTO(partUpdated);
    }

    @Override
    @Transactional
    public void delete(UUID partId) {

        log.info("Deleting part with ID: {}", partId);

        PartEntity part = validatePart(partId);

        part.deactivate();

        repository.save(part);

        log.info("Part deleted successfully with ID: {}", partId);
    }

    private PartEntity validatePart(UUID partId) {
        return repository.findById(partId)
                .orElseThrow(() -> {

                    log.warn("Part not found for deletion with ID: {}", partId);

                    return new PartNotFoundException(partId);
                });

    }
}
