package com.github.reenatobruno.parts_api.entity;

import com.github.reenatobruno.parts_api.util.DealershipDomainValidation;
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
@Table(name = "tb_dealer",
indexes = {
        @Index(name = "idx_dealer_cnpj", columnList = "dealer_cnpj"),
        @Index(name = "idx_dealer_name", columnList = "dealer_name"),

})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("active = true")
public class DealershipEntity {

    private static final int MAX_CNPJ_LENGTH = 14;
    private static final int MAX_DEALER_NAME_LENGTH = 150;
    private static final int MAX_TRADE_NAME_LENGTH = 150;
    private static final int MAX_EMAIL_LENGTH = 150;
    private static final int MAX_PHONE_LENGTH = 15;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID dealerId;

    @Column(name = "dealer_cnpj", nullable = false, unique = true, length = MAX_CNPJ_LENGTH)
    private String cnpj;

    @Column(name = "dealer_name", unique = true, nullable = false, length = MAX_DEALER_NAME_LENGTH)
    private String dealerName;

    @Column(name = "trade_name", nullable = false, length = MAX_TRADE_NAME_LENGTH)
    private String tradeName;

    @Column(name = "dealer_email", nullable = false, unique = true, length = MAX_EMAIL_LENGTH)
    private String email;

    @Column(nullable = false, length = MAX_PHONE_LENGTH)
    private String phone;

    @Column(name = "dealer_active", nullable = false)
    private boolean active;

    @Embedded
    private Address address;

    @CreatedDate
    @Column(name = "dealer_created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedBy
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    public DealershipEntity(String cnpj, String dealerName, String tradeName, String email, String phone, Address address) {
        setCnpj(cnpj);
        setDealerName(dealerName);
        setTradeName(tradeName);
        setEmail(email);
        setPhone(phone);
        this.active = true;
        this.address = address;
    }

    public UUID getDealerId() {
        return dealerId;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getDealerName() {
        return dealerName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isActive() {
        return active;
    }

    public Address getAddress() { return address; }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    private void setCnpj(String cnpj) {
        String stripCnpj = DealershipDomainValidation.normalize(cnpj);
        this.cnpj = DealershipDomainValidation.requireCnpj(stripCnpj, "CNPJ", MAX_CNPJ_LENGTH);
    }
    private void setDealerName(String dealerName) {
        String stripDealerName = DealershipDomainValidation.normalize(dealerName);
        String capitalized = StringUtils.capitalize(stripDealerName);
        this.dealerName = DealershipDomainValidation.requireNonBlank(capitalized, "Dealer name", MAX_DEALER_NAME_LENGTH);
    }

    private void setTradeName(String tradeName) {
        String stripTradeName = DealershipDomainValidation.normalize(tradeName);
        String capitalized = StringUtils.capitalize(stripTradeName);
        this.tradeName = DealershipDomainValidation.requireNonBlank(capitalized, "Trade name", MAX_TRADE_NAME_LENGTH);
    }

    private void setEmail(String email) {
        String stripEmail = DealershipDomainValidation.normalize(email);
        String lowerCase =  DealershipDomainValidation.lowerCase(stripEmail);
        this.email = DealershipDomainValidation.requireEmail(lowerCase, "E-mail", MAX_EMAIL_LENGTH);
    }

    private void setPhone(String phone) {
        String stripPhone = DealershipDomainValidation.normalize(phone);
        String filterPhoneCharacters = DealershipDomainValidation.filterOnlyDigits(stripPhone);
        this.phone = DealershipDomainValidation.requireNonBlank(filterPhoneCharacters, "Phone", MAX_PHONE_LENGTH);
    }

    public void deactivate() {this.active = false;}

    public void updateFields(String dealerName, String tradeName, String email, String phone, Address address) {

        if (dealerName != null) setDealerName(dealerName);
        if (tradeName != null) setTradeName(tradeName);
        if (email != null) setEmail(email);
        if (phone != null) setPhone(phone);
        if (address != null) this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealershipEntity dealership)) return false;
        return cnpj != null && cnpj.equals(dealership.getCnpj());
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnpj);
    }
}
