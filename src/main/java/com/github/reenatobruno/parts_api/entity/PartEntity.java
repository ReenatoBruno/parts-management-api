package com.github.reenatobruno.parts_api.entity;

import com.github.reenatobruno.parts_api.util.PartDomainValidation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_parts")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PartEntity {

        private static final int MAX_PART_NUMBER_LENGTH = 50;
        private static final int MAX_NAME_LENGTH = 100;
        private static final int MAX_SUPPLIER_LENGTH = 100;
        private static final int MAX_DESCRIPTION_LENGTH = 500;

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(nullable = false, unique = true, updatable = false, length = MAX_PART_NUMBER_LENGTH)
        private String partNumber;

        @Column(name = "part_name", nullable = false, length = MAX_NAME_LENGTH)
        private String partName;

        @Column(nullable = false, precision = 10, scale = 2)
        private BigDecimal price;

        @Column(nullable = false)
        private Integer quantity;

        @Column(nullable = false, length = MAX_SUPPLIER_LENGTH )
        private String supplier;

        @Column(name = "part_description", length = MAX_DESCRIPTION_LENGTH)
        private String description;

        @CreatedDate
        @Column(name = "part_created_at", updatable = false, nullable = false)
        private Instant createdAt;

        @LastModifiedDate
        @Column(name = "part_updated_at", nullable = false)
        private Instant updatedAt;

        @CreatedBy
        @Column(name = "part_created_by", nullable = false, updatable = false)
        private String createdBy;

        @LastModifiedBy
        @Column(name = "part_updated_by", nullable = false)
        private String updatedBy;

        public PartEntity(String partNumber, String partName, BigDecimal price, Integer quantity, String supplier, String description) {

                setPartNumber(partNumber);
                setPartName(partName);
                setPrice(price);
                setQuantity(quantity);
                setSupplier(supplier);
                setDescription(description);
        }

        public UUID getId() {
                return id;
        }

        public String getPartNumber() {
                return partNumber;
        }

        public BigDecimal getPrice() {
                return price;
        }

        public String getPartName() {
                return partName;
        }

        public Integer getQuantity() {
                return quantity;
        }

        public String getSupplier() {
                return supplier;
        }

        public String getDescription() {
                return description;
        }

        public Instant getCreatedAt() {
                return createdAt;
        }

        public Instant getUpdatedAt() {
                return updatedAt;
        }

        private void setPartNumber(String partNumber) {
                String partNumberNormalized = PartDomainValidation.normalize(partNumber);
                String upperCase = partNumberNormalized != null ? partNumberNormalized.toUpperCase() : null;
                this.partNumber = PartDomainValidation.requireValidPartNumber(upperCase, "Part number", MAX_PART_NUMBER_LENGTH);
        }

        private void setPartName(String partName) {
                String partNameNormalized = PartDomainValidation.normalize(partName);
                this.partName = PartDomainValidation.requireNonBlank(partNameNormalized, "Part name", MAX_NAME_LENGTH);
        }

        private void setPrice(BigDecimal price) {
                BigDecimal priceNormalized = price != null ? price.setScale(2, RoundingMode.HALF_UP) : null;
                this.price = PartDomainValidation.requirePositivePrice(priceNormalized, "Price");
        }

        private void setQuantity(Integer quantity) {
                this.quantity = PartDomainValidation.requirePositiveQuantity(quantity, "Quantity");
        }

        private void setSupplier(String supplier) {
                String supplierNormalized = PartDomainValidation.normalize(supplier);
                this.supplier = PartDomainValidation.requireNonBlank(supplierNormalized, "Supplier", MAX_SUPPLIER_LENGTH);
        }

        private void setDescription(String description) {
                String descriptionNormalized = description != null ? description.strip() : null;
                this.description = PartDomainValidation.requireNonBlankIfPresent(descriptionNormalized, "Description", MAX_DESCRIPTION_LENGTH);
        }

        public void updateFields(String partName, BigDecimal price, Integer quantity, String supplier, String description) {

                setPartName(partName);
                setPrice(price);
                setQuantity(quantity);
                setSupplier(supplier);
                setDescription(description);
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof PartEntity part)) return false;
                return partNumber != null && partNumber.equals((part.getPartNumber()));
        }

        @Override
        public int hashCode() {
                return Objects.hash(partNumber);
        }
}