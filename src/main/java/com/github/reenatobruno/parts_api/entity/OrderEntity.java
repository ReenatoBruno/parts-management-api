package com.github.reenatobruno.parts_api.entity;

import com.github.reenatobruno.parts_api.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_order",
        indexes = {
                @Index(name = "idx_order_dealership", columnList = "dealership_id"),
                @Index(name = "idx_order_status", columnList = "order_status")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("order_active = true")
public class OrderEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "order_id")
        private UUID orderId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "dealership_id", nullable = false)
        private DealershipEntity dealership;

        @Enumerated(EnumType.STRING)
        @Column(name = "order_status", nullable = false)
        private OrderStatus status;

        @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
        private BigDecimal totalAmount;

        @Column(name = "order_active", nullable = false)
        private boolean active = true;

        @CreatedDate
        @Column(name = "order_created_at", nullable = false, updatable = false)
        private Instant createdAt;

        @LastModifiedDate
        @Column(name = "order_updated_at", nullable = false)
        private Instant updatedAt;

        @CreatedBy
        @Column(name = "order_created_by", nullable = false, updatable = false)
        private String createdBy;

        @LastModifiedBy
        @Column(name = "order_updated_by", nullable = false)
        private String updatedBy;

        public OrderEntity(DealershipEntity dealership) {
            setDealership(dealership);
            this.status = OrderStatus.PENDING;
            this.totalAmount = BigDecimal.ZERO;
            this.active = true;
        }

        public UUID getOrderId() { return orderId; }

        public DealershipEntity getDealership() { return dealership; }

        public OrderStatus getStatus() { return status; }

        public BigDecimal getTotalAmount() { return totalAmount; }

        public boolean isActive() { return active; }

        public Instant getCreatedAt() { return createdAt; }

        public Instant getUpdatedAt() { return updatedAt; }

        public String getCreatedBy() { return createdBy; }

        public String getUpdatedBy() { return updatedBy; }

        private void setDealership(DealershipEntity dealership) {
            if (dealership == null) throw new IllegalArgumentException("Dealership is required");
            this.dealership = dealership;
        }

        public void confirm() {
            if (this.status != OrderStatus.PENDING) {
                throw new IllegalStateException("Only PENDING orders can be confirmed");
            }
            this.status = OrderStatus.CONFIRMED;
        }

        public void ship() {
            if (this.status != OrderStatus.CONFIRMED) {
                throw new IllegalStateException("Only CONFIRMED orders can be shipped");
            }
            this.status = OrderStatus.SHIPPED;
        }

        public void deliver() {
            if (this.status != OrderStatus.SHIPPED) {
                throw new IllegalStateException("Only SHIPPED orders can be delivered");
            }
            this.status = OrderStatus.DELIVERED;
        }

        public void cancel() {
            if (this.status == OrderStatus.DELIVERED) {
                throw new IllegalStateException("DELIVERED orders cannot be cancelled");
            }
            this.status = OrderStatus.CANCELLED;
        }

        public void updateTotalAmount(BigDecimal totalAmount) {
            if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Total amount must be positive");
            }
            this.totalAmount = totalAmount;
        }

        public void deactivate() { this.active = false; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OrderEntity order)) return false;
            return orderId != null && orderId.equals(order.getOrderId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId);
        }
}