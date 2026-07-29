package com.github.reenatobruno.parts_api.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.github.reenatobruno.parts_api.entity.DealershipEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DealershipRepository extends JpaRepository<DealershipEntity, UUID> {

    boolean existsByCnpj(String cnpj);

    Page<DealershipEntity> findAllByDealerNameContainingIgnoreCase(String dealerName, Pageable pageable);
}
