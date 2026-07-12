package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.CategoryRequestDTO;
import com.github.reenatobruno.parts_api.dto.CategoryResponseDTO;
import com.github.reenatobruno.parts_api.dto.CategoryUpdateDTO;
import com.github.reenatobruno.parts_api.entity.CategoryEntity;
import com.github.reenatobruno.parts_api.exception.CategoryAlreadyExistsException;
import com.github.reenatobruno.parts_api.exception.CategoryHasPartsException;
import com.github.reenatobruno.parts_api.exception.CategoryNotFoundException;
import com.github.reenatobruno.parts_api.mapper.CategoryMapper;
import com.github.reenatobruno.parts_api.repository.CategoryRepository;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper mapper;
    private final CategoryRepository repository;
    private final PartRepository partRepository;

    public CategoryServiceImpl(CategoryMapper mapper, CategoryRepository repository, PartRepository partRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.partRepository = partRepository;
    }

    @Override
    @Transactional
    public CategoryResponseDTO create(CategoryRequestDTO requestDTO) {

        if (repository.existsByCategoryName(requestDTO.categoryName())) {

            log.warn("Category already exists {}", requestDTO.categoryName());

            throw new CategoryAlreadyExistsException(requestDTO.categoryName());
        }

        CategoryEntity category = mapper.toEntity(requestDTO);

        try {
            CategoryEntity saveCategory = repository.save(category);

            log.info("Category created successfully with ID: {}", saveCategory.getCategoryId());

            return mapper.toResponse(saveCategory);
        } catch (DataIntegrityViolationException e) {

            log.error("Database integrity violation while creating category: {}", requestDTO.categoryName(), e);

            throw new CategoryAlreadyExistsException(requestDTO.categoryName());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponseDTO> getAll(String categoryName, Pageable pageable) {
        if (categoryName == null || categoryName.isBlank())
            return repository.findAll(pageable)
                    .map(mapper::toResponse);

        log.info("Filtering categories by name: {}", categoryName);

        return repository.findAllByCategoryNameContainsIgnoreCase(categoryName, pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public CategoryResponseDTO update(UUID categoryId, CategoryUpdateDTO updateDTO) {

        CategoryEntity existingCategory = findByCategoryId(categoryId);

        mapper.updateEntity(existingCategory, updateDTO);

        CategoryEntity saveCategory = repository.save(existingCategory);

        log.info("Category updated successfully with ID: {}", saveCategory.getCategoryId());

        return mapper.toResponse(saveCategory);
    }

    @Override
    @Transactional
    public void delete (UUID categoryId) {

        CategoryEntity category = findByCategoryId(categoryId);

        if (partRepository.existsByCategory((category))) {

            log.warn("Cannot deactivate category with ID: {} because it has parts associated", categoryId);

            throw new CategoryHasPartsException(categoryId);
        }

        category.deactivate();

        repository.save(category);

        log.info("Category deactivated successfully with ID: {}", categoryId);
    }

    private CategoryEntity findByCategoryId(UUID categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(() -> {
                    log.warn("Category not found with id: {}", categoryId);
                    return new CategoryNotFoundException(categoryId);
                });
    }
}
