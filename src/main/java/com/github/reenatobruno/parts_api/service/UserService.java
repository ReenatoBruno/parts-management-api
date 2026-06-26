package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.UserRequestDTO;
import com.github.reenatobruno.parts_api.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO create(UserRequestDTO requestDTO);
}
