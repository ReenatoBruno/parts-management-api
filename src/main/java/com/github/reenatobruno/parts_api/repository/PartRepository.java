package com.github.reenatobruno.parts_api.repository;

import com.github.reenatobruno.parts_api.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Long> {

}
