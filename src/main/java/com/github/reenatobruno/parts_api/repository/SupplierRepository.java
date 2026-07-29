package com.github.reenatobruno.parts_api.repository;

import com.github.reenatobruno.parts_api.entity.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.UUID;

public interface SupplierRepository extends JpaRepository<SupplierEntity, UUID> {

    boolean existsByCnpj(String cnpj);

    Page<SupplierEntity> findAllByCompanyNameContainingIgnoreCase(String companyName, Pageable pageable);
}
