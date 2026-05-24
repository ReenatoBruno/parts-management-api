package com.github.reenatobruno.parts_api.entity;

import com.github.reenatobruno.parts_api.util.PartDomainValidation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_parts")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Part {

        private static final int MAX_PART_NUMBER_LENGTH = 50;
        private static final int MAX_NAME_LENGTH = 100;
        private static final int MAX_SUPPLIER_LENGTH = 100;
        private static final int MAX_DESCRIPTION_LENGTH = 500;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true, updatable = false, length = MAX_PART_NUMBER_LENGTH)
        private String partNumber;

        @Column(nullable = false, length = MAX_NAME_LENGTH)
        private String name;

        @Column(nullable = false, precision = 10, scale = 2)
        private BigDecimal price;

        @Column(nullable = false)
        private Integer quantity;

        @Column(nullable = false, length = MAX_SUPPLIER_LENGTH )
        private String supplier;

        @Column(length = MAX_DESCRIPTION_LENGTH)
        private String description;

        @CreatedDate
        @Column(updatable = false, nullable = false)
        private Instant createdAt;

        @LastModifiedDate
        @Column(nullable = false)
        private Instant updatedAt;


        public Part(String partNumber, String name, BigDecimal price, Integer quantity, String supplier, String description) {

                setPartNumber(partNumber);
                setName(name);
                setPrice(price);
                setQuantity(quantity);
                setSupplier(supplier);
                setDescription(description);
        }

        private void setPartNumber(String partNumber) {
                this.partNumber = PartDomainValidation.requireValidPartNumber(partNumber, "Part number", MAX_PART_NUMBER_LENGTH);
        }

        private void setName(String name) {
                this.name = PartDomainValidation.requireNonBlank(name, "Part's name", MAX_NAME_LENGTH);
        }

        private void setPrice(BigDecimal price) {
                this.price = PartDomainValidation.requirePositivePrice(price, "Part's price");
        }

        private void setQuantity(Integer quantity) {
                this.quantity = PartDomainValidation.requirePositiveQuantity(quantity, "Quantity");
        }

        private void setSupplier(String supplier) {
                this.supplier = PartDomainValidation.requireNonBlank(supplier, "Supplier", MAX_SUPPLIER_LENGTH);
        }

        private void setDescription(String description) {
                this.description = PartDomainValidation.requireNonBlankIfPresent(description, "Description", MAX_DESCRIPTION_LENGTH);
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Part part = (Part) o;
                return partNumber != null && partNumber.equals((part.getPartNumber()));
        }

        @Override
        public int hashCode() {
                return Objects.hash(partNumber);
        }

        public void updateFields(String name, BigDecimal price, Integer quantity, String supplier, String description) {

                setName(name);
                setPrice(price);
                setQuantity(quantity);
                setSupplier(supplier);
                setDescription(description);
        }
}