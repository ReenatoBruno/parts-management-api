package com.github.reenatobruno.parts_api.entity;

import com.github.reenatobruno.parts_api.util.PartDomainValidation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PARTS")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Part {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true)
        private String partNumber;

        private String name;

        private BigDecimal price;

        private Integer quantity;

        private String supplier;

        private String description;

        @CreatedDate
        @Column(updatable = false, nullable = false)
        private LocalDateTime createdAt;

        @LastModifiedDate
        @Column(nullable = false)
        private LocalDateTime updatedAt;


        public Part(String partNumber, String name, BigDecimal price, Integer quantity, String supplier, String description) {

                setPartNumber(partNumber);
                setName(name);
                setPrice(price);
                setQuantity(quantity);
                setSupplier(supplier);
                setDescription(description);
        }

        public void setPartNumber(String partNumber) {
                this.partNumber = PartDomainValidation.requireNonBlank(partNumber, "Part number");
        }

        public void setName(String name) {
                this.name = PartDomainValidation.requireNonBlank(name, "Part's name");
        }

        public void setPrice(BigDecimal price) {
                this.price = PartDomainValidation.requirePositivePrice(price, "Part's price");
        }

        public void setQuantity(Integer quantity) {
                this.quantity = PartDomainValidation.requirePositiveOrZero(quantity, "Quantity");
        }

        public void setSupplier(String supplier) {
                this.supplier = PartDomainValidation.requireNonBlank(supplier, "Supplier");
        }

        public void setDescription(String description) {
                this.description = PartDomainValidation.requireNonBlankOrNull(description, "Description");
        }

        public void updateFields(String partNumber, String name, BigDecimal price, Integer quantity, String supplier, String description) {

                setPartNumber(partNumber);
                setName(name);
                setPrice(price);
                setQuantity(quantity);
                setSupplier(supplier);
                setDescription(description);
        }
}