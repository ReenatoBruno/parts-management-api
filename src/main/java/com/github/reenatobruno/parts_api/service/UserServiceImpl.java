package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.UserChangePasswordDTO;
import com.github.reenatobruno.parts_api.dto.UserRequestDTO;
import com.github.reenatobruno.parts_api.dto.UserResponseDTO;
import com.github.reenatobruno.parts_api.dto.UserUpdateDTO;
import com.github.reenatobruno.parts_api.entity.UserEntity;
import com.github.reenatobruno.parts_api.exception.*;
import com.github.reenatobruno.parts_api.mapper.UserMapper;
import com.github.reenatobruno.parts_api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getById(UUID userId) {
        return mapper.toResponse(findByUserId(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAll(String userName, Pageable pageable) {

        if (userName == null || userName.isBlank()) {
            return repository.findAll(pageable)
                    .map(mapper::toResponse);
        }
        return repository.findAllByUserNameContainingIgnoreCase(userName, pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public UserResponseDTO update(UUID userId, UserUpdateDTO updateDTO) {

        UserEntity user = findByUserId(userId);

        if (updateDTO.userEmail() != null && !updateDTO.userEmail().equals(user.getUserEmail())) {

            if (repository.existsByUserEmail(updateDTO.userEmail())) {
                throw new UserEmailAlreadyExistsException(updateDTO.userEmail());
            }
        }

        mapper.updateEntity(user, updateDTO);

        UserEntity saveUser = repository.save(user);

        log.info("User updated successfully for ID: {}", userId);

        return mapper.toResponse(saveUser);
    }

    @Override
    @Transactional
    public void changePassword(UUID userId, UserChangePasswordDTO passwordDTO) {

        UserEntity user = findByUserId(userId);

        if (!passwordEncoder.matches(passwordDTO.currentPassword(), user.getUserPassword())) {
            throw new UserInvalidPasswordException();
        }

        String encodedNewPassword = passwordEncoder.encode(passwordDTO.newPassword());

        user.changePassword(encodedNewPassword);

        repository.save(user);

        log.info("User password changed successfully for userId: {}", userId);
    }

    @Override
    @Transactional
    public void delete(UUID userId) {

        UserEntity user = findByUserId(userId);

        if (!user.isAccountEnabled()) {
            throw new UserAlreadyDeactivatedException();
        }

        user.deactivate();

        log.info("User ID: {} was successfully deactivated.", userId);

        repository.save(user);
    }

    private void validateUser(UserRequestDTO requestDTO) {

        if (repository.existsByUserCpf(requestDTO.userCpf())) {
            throw new UserCpfAlreadyExistsException(requestDTO.userCpf());
        }

        if (repository.existsByUserEmail(requestDTO.userEmail())) {
            throw new UserEmailAlreadyExistsException(requestDTO.userEmail());
        }
    }

    private UserEntity findByUserId(UUID userId) {
        return repository.findById(userId)
                .orElseThrow(() -> {

                    log.warn("User not found with id: {}", userId);

                    return new UserNotFoundException(userId);
                });
    }
}
