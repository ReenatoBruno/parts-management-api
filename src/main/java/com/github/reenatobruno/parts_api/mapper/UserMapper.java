package com.github.reenatobruno.parts_api.mapper;

import com.github.reenatobruno.parts_api.dto.UserRequestDTO;
import com.github.reenatobruno.parts_api.dto.UserResponseDTO;
import com.github.reenatobruno.parts_api.dto.UserUpdateDTO;
import com.github.reenatobruno.parts_api.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity toEntity(UserRequestDTO requestDTO, String encodedPassword) {
        return new UserEntity(
                requestDTO.userName(),
                requestDTO.userCpf(),
                requestDTO.userEmail(),
                requestDTO.userPassword()
        );
    }

    public UserResponseDTO toResponse(UserEntity user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public void updateEntity (UserEntity user, UserUpdateDTO updateDTO) {
        user.updateFields(
                updateDTO.userName(),
                updateDTO.userEmail()
        );
    }
}
