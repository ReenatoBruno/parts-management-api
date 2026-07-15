package com.github.reenatobruno.parts_api.repository;

import com.github.reenatobruno.parts_api.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<SupplierEntity, UUID> {
}
