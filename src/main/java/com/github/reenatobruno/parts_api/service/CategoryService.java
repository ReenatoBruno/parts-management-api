package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.CategoryRequestDTO;
import com.github.reenatobruno.parts_api.dto.CategoryResponseDTO;
import com.github.reenatobruno.parts_api.dto.CategoryUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CategoryService {

    CategoryResponseDTO create(CategoryRequestDTO requestDTO);

    Page<CategoryResponseDTO> getAll(String categoryName, Pageable pageable);

    CategoryResponseDTO update(UUID categoryId, CategoryUpdateDTO categoryUpdateDTO);

    void delete(UUID categoryID);
}
