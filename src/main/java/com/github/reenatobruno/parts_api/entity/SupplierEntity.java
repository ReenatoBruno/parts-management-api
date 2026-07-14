package com.github.reenatobruno.parts_api.entity;

import com.github.reenatobruno.parts_api.util.StringUtils;
import com.github.reenatobruno.parts_api.util.SupplierDomainValidation;
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
    private static final int MAX_COMPANY_NAME_LENGTH = 150;
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
    @Column(name = "supplier_id")
    private UUID supplierId;

    @Column(name = "supplier_cnpj", nullable = false, unique = true, length = MAX_CNPJ_LENGTH)
    private String cnpj;

    @Column(name = "supplier_name", nullable = false, length = MAX_COMPANY_NAME_LENGTH)
    private String companyName;

    @Column(name = "trade_name", nullable = false, unique = true, length = MAX_TRADE_NAME_LENGTH)
    private String tradeName;

    @Column(name = "supplier_email", nullable = false, length = MAX_EMAIL_LENGTH)
    private String email;

    @Column(name = "supplier_phone", nullable = false, length = MAX_PHONE_LENGTH)
    private String phone;

    @Column(nullable = false, length = MAX_ZIP_LENGTH)
    private String zip;

    @Column(nullable = false, length = MAX_STREET_LENGTH)
    private String street;

    @Column(name = "house_number", nullable = false, length = MAX_NUMBER_LENGTH)
    private String number;

    @Column(length = MAX_COMPLEMENT_LENGTH)
    private String complement;

    @Column(nullable = false, length = MAX_DISTRICT_LENGTH)
    private String district;

    @Column(nullable = false, length = MAX_CITY_LENGTH)
    private String city;

    @Column(nullable = false, length = MAX_STATE_LENGTH)
    private String state;

    @Column(name = "supplier_active", nullable = false)
    private boolean active;

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

    public SupplierEntity(String cnpj, String companyName, String tradeName, String email, String phone, String zip, String street, String number, String complement, String district, String city, String state) {

        setCnpj(cnpj);
        setCompanyName(companyName);
        setTradeName(tradeName);
        setEmail(email);
        setPhone(phone);
        setZip(zip);
        setStreet(street);
        setNumber(number);
        setComplement(complement);
        setDistrict(district);
        setCity(city);
        setState(state);
        this.active = true;
    }

    public UUID getSupplierId() {
        return supplierId;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public String getEmail() { return email; }

    public String getPhone() {
        return phone;
    }

    public String getZip() {
        return zip;
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

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() { return createdAt; }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() { return updatedBy; }

    private void setCnpj(String cnpj) {
        String stripCnpj = SupplierDomainValidation.normalize(cnpj);
        this.cnpj = SupplierDomainValidation.requireCnpj(stripCnpj, "CNPJ", MAX_CNPJ_LENGTH);
    }
    private void setCompanyName(String companyName) {
        String stripCompanyName = SupplierDomainValidation.normalize(companyName);
        String capitalized = StringUtils.capitalize(stripCompanyName);
        this.companyName = SupplierDomainValidation.requireNonBlank(capitalized, "Company name", MAX_COMPANY_NAME_LENGTH);
    }

    private void setTradeName(String tradeName) {
        String stripTradeName = SupplierDomainValidation.normalize(tradeName);
        String capitalized = StringUtils.capitalize(stripTradeName);
        this.companyName = SupplierDomainValidation.requireNonBlank(capitalized, "Trade name", MAX_TRADE_NAME_LENGTH);
    }

    private void setEmail(String email) {
        String stripEmail = SupplierDomainValidation.normalize(email);
        String lowerCase =  SupplierDomainValidation.lowerCase(stripEmail);
        this.email = SupplierDomainValidation.requireEmail(lowerCase, "E-mail", MAX_EMAIL_LENGTH);
    }

    private void setPhone(String phone) {
        String stripPhone = SupplierDomainValidation.normalize(phone);
        String filterPhoneCharacters = SupplierDomainValidation.filterOnlyDigits(stripPhone);
        this.phone = SupplierDomainValidation.requireNonBlank(filterPhoneCharacters, "Phone", MAX_PHONE_LENGTH);
    }

    private void setZip(String zip) {
        String stripZip = SupplierDomainValidation.normalize(zip);
        String filterZipCharacters = SupplierDomainValidation.filterOnlyDigits(stripZip);
        this.zip = SupplierDomainValidation.requireNonBlank(filterZipCharacters, "Zip", MAX_ZIP_LENGTH);
    }

    private void setStreet(String street) {
        String stripStreet = SupplierDomainValidation.normalize(street);
        String capitalized = StringUtils.capitalize(stripStreet);
        this.companyName = SupplierDomainValidation.requireNonBlank(capitalized, "Street", MAX_STREET_LENGTH);
    }

    private void setNumber(String number) {
        String stripNumber = SupplierDomainValidation.normalize(number);
        String filterNumberCharacters = SupplierDomainValidation.sanitizeText(stripNumber);
        String upperCase = SupplierDomainValidation.upperCase(filterNumberCharacters);
        this.number = SupplierDomainValidation.requireNonBlank(upperCase, "Number", MAX_NUMBER_LENGTH);
    }

    private void setComplement(String complement) {
        String stripComplement = SupplierDomainValidation.normalize(complement);
        String filterComplementCharacters = SupplierDomainValidation.sanitizeText(stripComplement);
        String upperCase = SupplierDomainValidation.upperCase(filterComplementCharacters);
        this.complement = SupplierDomainValidation.requireNonBlankIfPresent(upperCase, "Complement", MAX_COMPLEMENT_LENGTH);
    }

    private void setDistrict(String district) {
        String stripDistrict = SupplierDomainValidation.normalize(district);
        String capitalized = StringUtils.capitalize(stripDistrict);
        this.district = SupplierDomainValidation.requireNonBlank(capitalized, "District", MAX_DISTRICT_LENGTH);
    }

    private void setCity(String city) {
        String stripCity = SupplierDomainValidation.normalize(city);
        String capitalized = StringUtils.capitalize(stripCity);
        this.city = SupplierDomainValidation.requireNonBlank(capitalized, "City", MAX_CITY_LENGTH);
    }

    private void setState(String state) {
        String stripState = SupplierDomainValidation.normalize(state);
        String upperCase = SupplierDomainValidation.upperCase(stripState);
        this.state = SupplierDomainValidation.requireNonBlank(upperCase, "State", MAX_STATE_LENGTH);
    }

    public void deactivate() {
        this.active = false;
    }

    public void updateFields(String cnpj, String companyName, String tradeName, String email, String phone, String zip, String street, String number, String complement, String district, String city, String state) {

        setCnpj(cnpj);
        setCompanyName(companyName);
        setTradeName(tradeName);
        setEmail(email);
        setPhone(phone);
        setZip(zip);
        setStreet(street);
        setNumber(number);
        setComplement(complement);
        setDistrict(district);
        setCity(city);
        setState(state);
    }
}
