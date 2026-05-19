package com.github.reenatobruno.parts_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PartResponseDTO {

    private Long id;
    private String partNumber;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String supplier;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
