package com.github.reenatobruno.parts_api.entity;

import com.github.reenatobruno.parts_api.util.PartDomainValidation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_parts_api")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PartEntity {

        private static final int MAX_PART_NUMBER_LENGTH = 50;
        private static final int MAX_NAME_LENGTH = 100;
        private static final int MAX_SUPPLIER_LENGTH = 100;
        private static final int MAX_DESCRIPTION_LENGTH = 500;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true, updatable = false, length = MAX_PART_NUMBER_LENGTH)
        private String partNumber;

        @Column(name = "part_name", nullable = false, length = MAX_NAME_LENGTH)
        private String name;

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


        public PartEntity(String partNumber, String name, BigDecimal price, Integer quantity, String supplier, String description) {

                setPartNumber(partNumber);
                setName(name);
                setPrice(price);
                setQuantity(quantity);
                setSupplier(supplier);
                setDescription(description);
        }

        public Long getId() {
                return id;
        }

        public String getPartNumber() {
                return partNumber;
        }

        public BigDecimal getPrice() {
                return price;
        }

        public String getName() {
                return name;
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
                String normalize = PartDomainValidation.normalize(partNumber);
                String upperCase = normalize != null ? normalize.toUpperCase() : null;
                this.partNumber = PartDomainValidation.requireValidPartNumber(upperCase, "Part number", MAX_PART_NUMBER_LENGTH);
        }

        private void setName(String name) {
                String normalized = PartDomainValidation.normalize(name);
                this.name = PartDomainValidation.requireNonBlank(normalized, "Part's name", MAX_NAME_LENGTH);
        }

        private void setPrice(BigDecimal price) {
                BigDecimal normalized = price != null ? price.setScale(2, RoundingMode.HALF_UP) : null;
                this.price = PartDomainValidation.requirePositivePrice(normalized, "Part's price");
        }

        private void setQuantity(Integer quantity) {
                this.quantity = PartDomainValidation.requirePositiveQuantity(quantity, "Quantity");
        }

        private void setSupplier(String supplier) {
                String normalized = PartDomainValidation.normalize(supplier);
                this.supplier = PartDomainValidation.requireNonBlank(normalized, "Supplier", MAX_SUPPLIER_LENGTH);
        }

        private void setDescription(String description) {
                String normalized = description != null ? description.strip() : null;
                this.description = PartDomainValidation.requireNonBlankIfPresent(normalized, "Description", MAX_DESCRIPTION_LENGTH);
        }

        public void updateFields(String name, BigDecimal price, Integer quantity, String supplier, String description) {

                setName(name);
                setPrice(price);
                setQuantity(quantity);
                setSupplier(supplier);
                setDescription(description);
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                PartEntity partEntity = (PartEntity) o;
                return partNumber != null && partNumber.equals((partEntity.getPartNumber()));
        }

        @Override
        public int hashCode() {
                return Objects.hash(partNumber);
        }
}