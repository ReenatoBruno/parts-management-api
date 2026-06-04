package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.PartRequestDTO;
import com.github.reenatobruno.parts_api.dto.PartResponseDTO;
import com.github.reenatobruno.parts_api.dto.PartUpdateDTO;
import com.github.reenatobruno.parts_api.entity.Part;
import com.github.reenatobruno.parts_api.infra.PartNotFoundException;
import com.github.reenatobruno.parts_api.infra.PartNumberAlreadyExistsException;
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

        log.info("Checking if part number already exists {}", request.getPartNumber());

        if (repository.existsByPartNumber(request.getPartNumber())) {

            log.warn("Part number already exists {}", request.getPartNumber());

            throw new PartNumberAlreadyExistsException(request.getPartNumber());
        }
        Part part = mapper.toEntity(request);

        Part saved = repository.save(part);

        log.debug("Part persisted successfully with ID: {}", saved.getId());

        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PartResponseDTO getById(Long id) {

        log.debug("Fetching part with ID: {}", id);

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

        log.info("Deleting part with ID: {}", id);

        Part part = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Part not found for deletion with ID: {}", id);

                    return new PartNotFoundException(id);
                });

        repository.delete(part);

        log.debug("Part deleted successfully with ID: {}", id);
    }
}
