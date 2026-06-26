package com.github.reenatobruno.parts_api.repository;

import com.github.reenatobruno.parts_api.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByUserCpf(String userCpf);

    boolean existsByUserEmail(String userEmail);

    Page<UserEntity> findAllByUserNameContainingIgnoreCase(String userName, Pageable pageable);
}
