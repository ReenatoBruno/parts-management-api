package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.DealershipRequestDTO;
import com.github.reenatobruno.parts_api.dto.DealershipResponseDTO;
import com.github.reenatobruno.parts_api.dto.DealershipUpdateDTO;
import com.github.reenatobruno.parts_api.dto.ViaCepResponseDTO;
import com.github.reenatobruno.parts_api.entity.DealershipEntity;
import com.github.reenatobruno.parts_api.exception.DealerAlreadyExistsException;
import com.github.reenatobruno.parts_api.exception.DealerDataConflictionException;
import com.github.reenatobruno.parts_api.exception.DealerNotFoundException;
import com.github.reenatobruno.parts_api.mapper.DealershipMapper;
import com.github.reenatobruno.parts_api.repository.DealershipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class DealershipServiceImpl implements DealershipService {

    private final DealershipMapper mapper;
    private final DealershipRepository repository;
    private final ViaCepService viaCepService;

    public DealershipServiceImpl(DealershipMapper mapper, DealershipRepository repository, ViaCepService viaCepService) {
        this.mapper = mapper;
        this.repository = repository;
        this.viaCepService = viaCepService;
    }

    @Override
    @Transactional
    public DealershipResponseDTO create(DealershipRequestDTO requestDTO) {

        validateDealer(requestDTO);

        ViaCepResponseDTO viaCepResponseDTO = fetchAddress(requestDTO.zip());

        DealershipEntity dealership = mapper.toEntity(requestDTO, viaCepResponseDTO);

        try {
            DealershipEntity saveDealer = repository.save(dealership);

            log.info("Dealership created successfully with ID: {}", saveDealer.getDealerId());

            return mapper.toResponse(saveDealer);
        } catch (DataIntegrityViolationException e) {

            log.error("Database integrity violation while creating dealership: {}", requestDTO.cnpj(), e);

            throw new DealerDataConflictionException("Dealer already registered", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DealershipResponseDTO getById(UUID dealerId) {

        log.info("Fetching dealer with ID: {}", dealerId);

        return mapper.toResponse(findByDealerId(dealerId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DealershipResponseDTO> getAll(String dealerName, Pageable pageable) {

        if ( dealerName == null || dealerName.isBlank()) {
            return repository.findAll(pageable)
                    .map(mapper::toResponse);
        }

        log.info("Fetching all dealers with filter dealer name {}", dealerName);

        return repository.findAllByDealerNameContainingIgnoreCase(dealerName, pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public DealershipResponseDTO update(UUID dealerId, DealershipUpdateDTO updateDTO) {

        log.info("Updating dealer with ID: {}", dealerId);

        DealershipEntity existingDealer = findByDealerId(dealerId);

        ViaCepResponseDTO address = null;

        if (updateDTO.zip() != null) {
            address = fetchAddress(updateDTO.zip());
        }

        mapper.updateEntity(existingDealer, updateDTO, address);

        DealershipEntity saveDealer = repository.save(existingDealer);

        log.info("Dealership updated successfully with ID: {}", saveDealer.getDealerId());

        return mapper.toResponse(saveDealer);
    }

    @Override
    @Transactional
    public void delete (UUID dealerId) {

        log.info("Deactivating dealer with ID: {}", dealerId);

        DealershipEntity dealership = findByDealerId(dealerId);

        dealership.deactivate();

        log.info("Dealership deactivated successfully with ID: {}", dealerId);

        repository.save(dealership);
    }

    private void validateDealer(DealershipRequestDTO requestDTO) {

        if (repository.existsByCnpj(requestDTO.cnpj())) {

            log.warn("Dealership already exists with CNPJ: {}", requestDTO.cnpj());

            throw new DealerAlreadyExistsException(requestDTO.cnpj());
        }
    }

    private ViaCepResponseDTO fetchAddress(String zip) {

        log.info("Fetching address for zip: {}", zip);

        return viaCepService.findByZip(zip);
    }

    private DealershipEntity findByDealerId(UUID dealerId) {
        return repository.findById(dealerId)
                .orElseThrow(() -> {

                    log.warn("Dealer not found with ID: {}", dealerId);

                    return new DealerNotFoundException(dealerId);
                });
    }
}
