package com.github.reenatobruno.parts_api.entity;

import com.github.reenatobruno.parts_api.util.DomainValidation;
import com.github.reenatobruno.parts_api.util.StringUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_supplier",
indexes = {
        @Index(name = "idx_supplier_cnpj", columnList = "supplier_cnpj"),
        @Index(name = "idx_trade_name", columnList = "trade_name")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("active = true")
public class SupplierEntity {

    private static final int MAX_CNPJ_LENGTH = 14;
    private static final int MAX_SUPPLIER_NAME_LENGTH = 150;
    private static final int MAX_TRADE_NAME_LENGTH = 150;
    private static final int MAX_EMAIL_LENGTH = 150;
    private static final int MAX_PHONE_LENGTH = 15;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "supplier_id")
    private UUID supplierId;

    @Column(name = "supplier_cnpj", nullable = false, unique = true, length = MAX_CNPJ_LENGTH)
    private String cnpj;

    @Column(name = "supplier_name", nullable = false, length = MAX_SUPPLIER_NAME_LENGTH)
    private String supplierName;

    @Column(name = "trade_name", nullable = false, unique = true, length = MAX_TRADE_NAME_LENGTH)
    private String tradeName;

    @Column(name = "supplier_email", nullable = false, unique = true, length = MAX_EMAIL_LENGTH)
    private String email;

    @Column(name = "supplier_phone", nullable = false, length = MAX_PHONE_LENGTH)
    private String phone;

    @Column(name = "supplier_active", nullable = false)
    private boolean active;

    @Embedded
    private Address address;

    @CreatedDate
    @Column(name = "supplier_created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "supplier_updated_at", nullable = false)
    private Instant updatedAt;

    @CreatedBy
    @Column(name = "supplier_created_by", nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "supplier_updated_by", nullable = false)
    private String updatedBy;

    public SupplierEntity(String cnpj, String supplierName, String tradeName, String email, String phone, Address address) {

        setCnpj(cnpj);
        setSupplierName(supplierName);
        setTradeName(tradeName);
        setEmail(email);
        setPhone(phone);
        this.active = true;
        this.address = address;
    }

    public UUID getSupplierId() {
        return supplierId;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public String getEmail() { return email; }

    public String getPhone() {
        return phone;
    }

    public boolean isActive() {
        return active;
    }

    public Address getAddress() { return address; }

    public Instant getCreatedAt() { return createdAt; }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() { return updatedBy; }

    private void setCnpj(String cnpj) {
        String stripCnpj = DomainValidation.normalize(cnpj);
        this.cnpj = DomainValidation.requireCnpj(stripCnpj, "CNPJ", MAX_CNPJ_LENGTH);
    }
    private void setSupplierName(String supplierName) {
        String stripCompanyName = DomainValidation.normalize(supplierName);
        String capitalized = StringUtils.capitalize(stripCompanyName);
        this.supplierName = DomainValidation.requireNonBlank(capitalized, "Company name", MAX_SUPPLIER_NAME_LENGTH);
    }

    private void setTradeName(String tradeName) {
        String stripTradeName = DomainValidation.normalize(tradeName);
        String capitalized = StringUtils.capitalize(stripTradeName);
        this.tradeName = DomainValidation.requireNonBlank(capitalized, "Trade name", MAX_TRADE_NAME_LENGTH);
    }

    private void setEmail(String email) {
        String stripEmail = DomainValidation.normalize(email);
        String lowerCase =  DomainValidation.lowerCase(stripEmail);
        this.email = DomainValidation.requireEmail(lowerCase, "E-mail", MAX_EMAIL_LENGTH);
    }

    private void setPhone(String phone) {
        String stripPhone = DomainValidation.normalize(phone);
        String filterPhoneCharacters = DomainValidation.filterZipAndPhoneCharacters(stripPhone);
        this.phone = DomainValidation.requireNonBlank(filterPhoneCharacters, "Phone", MAX_PHONE_LENGTH);
    }

    public void deactivate() {
        this.active = false;
    }

    public void updateFields(String supplierName, String tradeName, String email, String phone, Address address) {

        if (supplierName != null) setSupplierName(supplierName);
        if (tradeName != null) setTradeName(tradeName);
        if (email != null) setEmail(email);
        if (phone != null) setPhone(phone);
        if (address != null) this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierEntity supplier)) return false;
        return cnpj != null && cnpj.equals(supplier.getCnpj());
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnpj);
    }
}
