package com.github.reenatobruno.parts_api.entity;

import com.github.reenatobruno.parts_api.util.DomainValidation;
import com.github.reenatobruno.parts_api.util.StringUtils;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
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
@Table(name = "tb_parts",
indexes = {
        @Index(name = "idx_part_number", columnList = "part_number")
})
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@SQLRestriction("part_active = true")
public class PartEntity {

        private static final int MAX_PART_NUMBER_LENGTH = 50;
        private static final int MAX_NAME_LENGTH = 100;
        private static final int MAX_SUPPLIER_LENGTH = 100;
        private static final int MAX_DESCRIPTION_LENGTH = 255;

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "part_id")
        private UUID partId;

        @Column(nullable = false, unique = true, updatable = false, length = MAX_PART_NUMBER_LENGTH)
        private String partNumber;

        @Column(name = "part_name", nullable = false, length = MAX_NAME_LENGTH)
        private String partName;

        @Column(nullable = false, precision = 10, scale = 2)
        private BigDecimal price;

        @Column(nullable = false)
        private Integer quantity;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "supplier_id", nullable = false)
        private SupplierEntity supplier;

        @Column(name = "part_description", length = MAX_SUPPLIER_LENGTH)
        private String description;

        @Column(name = "part_active", nullable = false)
        private boolean active;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id", nullable = false)
        private CategoryEntity category;

        @CreatedDate
        @Column(name = "part_created_at", nullable = false, updatable = false)
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

        public PartEntity(String partNumber, String partName, BigDecimal price, Integer quantity, SupplierEntity supplier, String description, CategoryEntity category) {
                setPartNumber(partNumber);
                setPartName(partName);
                setPrice(price);
                setQuantity(quantity);
                setSupplier(supplier);
                setDescription(description);
                setCategory(category);
                this.active = true;
        }

        public UUID getPartId() {
                return partId;
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

        public SupplierEntity getSupplier() {
                return supplier;
        }

        public String getDescription() {
                return description;
        }

        public CategoryEntity getCategory() {
                return category;
        }

        public boolean isActive() { return active; }

        public Instant getCreatedAt() {
                return createdAt;
        }

        public Instant getUpdatedAt() {
                return updatedAt;
        }

        public String getCreatedBy() { return createdBy; }

        public String getUpdatedBy() { return updatedBy; }

        private void setPartNumber(String partNumber) {
                String stripPartNumber = DomainValidation.normalize(partNumber);
                String upperCase = stripPartNumber != null ? stripPartNumber.toUpperCase() : null;
                this.partNumber = DomainValidation.requireValidPartNumber(upperCase, "Part number", MAX_PART_NUMBER_LENGTH);
        }

        private void setPartName(String partName) {
                String stripPartName = DomainValidation.normalize(partName);
                String capitalized = StringUtils.capitalize(stripPartName);
                this.partName = DomainValidation.requireNonBlank(capitalized, "Part name", MAX_NAME_LENGTH);
        }

        private void setPrice(BigDecimal price) {
                BigDecimal stripPrice = price != null ? price.setScale(2, RoundingMode.HALF_UP) : null;
                this.price = DomainValidation.requirePositivePrice(stripPrice, "Price");
        }

        private void setQuantity(Integer quantity) {
                this.quantity = DomainValidation.requirePositiveQuantity(quantity, "Quantity");
        }

        private void setSupplier(SupplierEntity supplier) {
                this.supplier = supplier;
        }

        private void setDescription(String description) {
                String stripDescription = DomainValidation.normalize(description);
                String capitalizedFirst = stripDescription != null
                        ? stripDescription.substring(0, 1).toUpperCase() + stripDescription.substring(1).toLowerCase()
                        : null;
                this.description = DomainValidation.requireNonBlankIfPresent(capitalizedFirst, "Description", MAX_DESCRIPTION_LENGTH);
        }

        private void setCategory(CategoryEntity category) {
                this.category = category;
        }

        public void deactivate() {
                this.active = false;
        }

        public void updateFields(String partName, BigDecimal price, Integer quantity, String description) {
                setPartName(partName);
                setPrice(price);
                setQuantity(quantity);
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