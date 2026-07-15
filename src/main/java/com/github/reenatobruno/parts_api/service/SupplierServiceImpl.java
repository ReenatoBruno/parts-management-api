package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.SupplierRequestDTO;
import com.github.reenatobruno.parts_api.dto.SupplierResponseDTO;
import com.github.reenatobruno.parts_api.dto.SupplierUpdateDTO;
import com.github.reenatobruno.parts_api.dto.ViaCepResponseDTO;
import com.github.reenatobruno.parts_api.entity.SupplierEntity;
import com.github.reenatobruno.parts_api.exception.SupplierAlreadyExistsException;
import com.github.reenatobruno.parts_api.exception.SupplierDataConflictionException;
import com.github.reenatobruno.parts_api.exception.SupplierNotFoundException;
import com.github.reenatobruno.parts_api.mapper.SupplierMapper;
import com.github.reenatobruno.parts_api.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierMapper mapper;
    private final SupplierRepository repository;
    private final ViaCepService viaCepService;

    public SupplierServiceImpl(SupplierMapper mapper, SupplierRepository repository, ViaCepService viaCepService) {
        this.mapper = mapper;
        this.repository = repository;
        this.viaCepService = viaCepService;
    }

    @Override
    @Transactional
    public SupplierResponseDTO create(SupplierRequestDTO requestDTO) {

        if (repository.existsByCnpj((requestDTO.cnpj()))) {

            log.warn("Supplier already exists {}", requestDTO.cnpj());

            throw new SupplierAlreadyExistsException(requestDTO.cnpj());
        }

        ViaCepResponseDTO viaCepResponseDTO = viaCepService.findByZip(requestDTO.zip());

        SupplierEntity supplier = mapper.toEntity(requestDTO, viaCepResponseDTO);

        try {
            SupplierEntity saveSupplier = repository.save(supplier);

            log.info("Supplier created successfully with ID: {}", saveSupplier.getSupplierId());

            return mapper.toResponse(saveSupplier);
        } catch (DataIntegrityViolationException e) {

            log.error("Database integrity violation while creating supplier: {}", requestDTO.companyName(), e);

            throw new SupplierDataConflictionException("Supplier already registered", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierResponseDTO getById(UUID supplierId) {

        log.info("Fetching supplier with ID: {}", supplierId);

        return mapper.toResponse(findBySupplierId(supplierId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupplierResponseDTO> getAll(String companyName, Pageable pageable) {

        if (companyName == null || companyName.isBlank())
            return repository.findAll(pageable)
                    .map(mapper::toResponse);

        log.info("Fetching all suppliers with filter company name {}", companyName);

        return repository.findAllByCompanyNameContainingIgnoreCase(companyName, pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public SupplierResponseDTO update(UUID supplierId, SupplierUpdateDTO updateDTO) {

        log.info("Updating supplier with ID: {}", supplierId);

        SupplierEntity existingSupplier = findBySupplierId(supplierId);

        ViaCepResponseDTO address = viaCepService.findByZip(updateDTO.zip());

        mapper.updateEntity(existingSupplier, updateDTO, address);

        SupplierEntity saveSupplier = repository.save(existingSupplier);

        log.info("Supplier updated successfully with ID: {}", supplierId);

        return mapper.toResponse(saveSupplier);
    }

    @Override
    @Transactional
    public void delete(UUID supplierId) {

        log.info("Deleting supplier with ID: {}", supplierId);

        SupplierEntity supplier = findBySupplierId(supplierId);

        supplier.deactivate();

        repository.save(supplier);

        log.info("Supplier deleted successfully with ID: {}", supplierId);
    }

    private SupplierEntity findBySupplierId(UUID supplierId) {
        return repository.findById(supplierId)
                .orElseThrow(() -> {

                    log.warn("Supplier not found with ID: {}", supplierId);

                    return new SupplierNotFoundException(supplierId);
                });
    }
}
