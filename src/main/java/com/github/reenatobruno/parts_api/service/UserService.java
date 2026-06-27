package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.UserChangePasswordDTO;
import com.github.reenatobruno.parts_api.dto.UserRequestDTO;
import com.github.reenatobruno.parts_api.dto.UserResponseDTO;
import com.github.reenatobruno.parts_api.dto.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    UserResponseDTO create(UserRequestDTO requestDTO);

    UserResponseDTO getById(UUID userID);

    Page<UserResponseDTO> getAll(String userName, Pageable pageable);

    UserResponseDTO update(UUID userId, UserUpdateDTO updateDTO);

    void changePassword(UUID userId, UserChangePasswordDTO passwordDTO);

    void delete(UUID userId);
}
