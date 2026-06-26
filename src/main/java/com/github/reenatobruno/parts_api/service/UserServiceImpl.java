package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.UserRequestDTO;
import com.github.reenatobruno.parts_api.dto.UserResponseDTO;
import com.github.reenatobruno.parts_api.entity.UserEntity;
import com.github.reenatobruno.parts_api.exception.UserCpfAlreadyExistsException;
import com.github.reenatobruno.parts_api.exception.UserDataConflictionException;
import com.github.reenatobruno.parts_api.exception.UserEmailAlreadyExistsException;
import com.github.reenatobruno.parts_api.mapper.UserMapper;
import com.github.reenatobruno.parts_api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper mapper, UserRepository repository, PasswordEncoder passwordEncoder) {

        this.mapper = mapper;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponseDTO create(UserRequestDTO requestDTO) {

        validateUser(requestDTO);

        String encodedPassword = passwordEncoder.encode(requestDTO.userPassword());

        UserEntity user = mapper.toEntity(requestDTO, encodedPassword);

        try {
            UserEntity saveUser = repository.save(user);

            return mapper.toResponse(saveUser);
        } catch (DataIntegrityViolationException e) {
            throw new UserDataConflictionException("CPF or E-mail already registered", e);
        }
    }

    private void validateUser(UserRequestDTO requestDTO) {

        if (repository.existsByUserCpf(requestDTO.userCpf())) {
            throw new UserCpfAlreadyExistsException(requestDTO.userCpf());
        }

        if (repository.existsByUserEmail(requestDTO.userEmail())) {
            throw new UserEmailAlreadyExistsException(requestDTO.userEmail());
        }
    }
}
