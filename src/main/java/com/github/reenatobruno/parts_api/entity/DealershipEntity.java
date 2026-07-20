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
    private static final int MAX_ZIP_LENGTH = 9;
    private static final int MAX_STREET_LENGTH = 150;
    private static final int MAX_NUMBER_LENGTH = 10;
    private static final int MAX_COMPLEMENT_LENGTH = 50;
    private static final int MAX_DISTRICT_LENGTH = 50;
    private static final int MAX_CITY_LENGTH = 50;
    private static final int MAX_STATE_LENGTH = 2;

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

    @Column(nullable = false, length = MAX_ZIP_LENGTH)
    private String zipCode;

    @Column(nullable = false, length = MAX_STREET_LENGTH)
    private String street;

    @Column(nullable = false, length = MAX_NUMBER_LENGTH)
    private String number;

    @Column(nullable = true, length = MAX_COMPLEMENT_LENGTH)
    private String complement;

    @Column(nullable = false, length = MAX_DISTRICT_LENGTH)
    private String district;

    @Column(nullable = false, length = MAX_CITY_LENGTH)
    private String city;

    @Column(nullable = false, length = MAX_STATE_LENGTH)
    private String state;

    @Column(name = "dealer_active", nullable = false)
    private boolean active;

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

    public DealershipEntity(String cnpj, String dealerName, String tradeName, String email, String phone, String zipCode, String street, String number, String complement, String district, String city, String state) {
        setCnpj(cnpj);
        setDealerName(dealerName);
        setTradeName(tradeName);
        setEmail(email);
        setPhone(phone);
        setZipCode(zipCode);
        setStreet(street);
        setNumber(number);
        setComplement(complement);
        setDistrict(district);
        setCity(city);
        setState(state);
        this.active = true;
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

    public String getZipCode() {
        return zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getDistrict() { return district; }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public boolean isActive() {
        return active;
    }

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

    private void setZipCode(String zipCode) {
        String stripZip = DealershipDomainValidation.normalize(zipCode);
        String filterZipCharacters = DealershipDomainValidation.filterOnlyDigits(stripZip);
        this.zipCode = DealershipDomainValidation.requireNonBlank(filterZipCharacters, "Zip", MAX_ZIP_LENGTH);
    }

    private void setStreet(String street) {
        String stripStreet = DealershipDomainValidation.normalize(street);
        String capitalized = StringUtils.capitalize(stripStreet);
        this.street = DealershipDomainValidation.requireNonBlank(capitalized, "Street", MAX_STREET_LENGTH);
    }

    private void setNumber(String number) {
        String stripNumber = DealershipDomainValidation.normalize(number);
        String filterNumberCharacters = DealershipDomainValidation.sanitizeText(stripNumber);
        String upperCase = DealershipDomainValidation.upperCase(filterNumberCharacters);
        this.number = DealershipDomainValidation.requireNonBlank(upperCase, "Number", MAX_NUMBER_LENGTH);
    }

    private void setComplement(String complement) {
        String stripComplement = DealershipDomainValidation.normalize(complement);
        String filterComplementCharacters = DealershipDomainValidation.sanitizeText(stripComplement);
        String upperCase = DealershipDomainValidation.upperCase(filterComplementCharacters);
        this.complement = DealershipDomainValidation.requireNonBlankIfPresent(upperCase, "Complement", MAX_COMPLEMENT_LENGTH);
    }

    private void setDistrict(String district) {
        String stripDistrict = DealershipDomainValidation.normalize(district);
        String capitalized = StringUtils.capitalize(stripDistrict);
        this.district = DealershipDomainValidation.requireNonBlank(capitalized, "District", MAX_DISTRICT_LENGTH);
    }

    private void setCity(String city) {
        String stripCity = DealershipDomainValidation.normalize(city);
        String capitalized = StringUtils.capitalize(stripCity);
        this.city = DealershipDomainValidation.requireNonBlank(capitalized, "City", MAX_CITY_LENGTH);
    }

    private void setState(String state) {
        String stripState = DealershipDomainValidation.normalize(state);
        String upperCase = DealershipDomainValidation.upperCase(stripState);
        this.state = DealershipDomainValidation.requireNonBlank(upperCase, "State", MAX_STATE_LENGTH);
    }

    public void deactivate() {this.active = false;}

    public void updateFields(String dealerName, String tradeName, String email, String phone, String zipCode, String street, String number, String complement, String district, String city, String state) {

        setDealerName(dealerName);
        setTradeName(tradeName);
        setEmail(email);
        setPhone(phone);
        setZipCode(zipCode);
        setStreet(street);
        setNumber(number);
        setComplement(complement);
        setDistrict(district);
        setCity(city);
        setState(state);
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
