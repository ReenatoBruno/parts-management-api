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

    public String getPartNumber() {
        return partNumber;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
