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
    private static final int MAX_ZIP_LENGTH = 8;
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

    @Column(nullable = false, length = MAX_COMPLEMENT_LENGTH)
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
    private Instant created_at;

    @LastModifiedDate
    @Column(name = "supplier_updated_at", nullable = false)
    private Instant updated_at;

    @CreatedBy
    @Column(name = "supplier_created_by", nullable = false, updatable = false)
    private String created_by;

    @LastModifiedBy
    @Column(name = "supplier_updated_by", nullable = false)
    private String updated_by;

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

    public Instant getCreated_at() { return created_at; }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getUpdated_by() { return updated_by; }

    private void setCompanyName(String companyName) {
        String normalizedCompanyName = SupplierDomainValidation.normalize(companyName);
        String capitalized = StringUtils.capitalize(normalizedCompanyName);
        this.companyName = SupplierDomainValidation.require_non_blank(capitalized, "Company name", MAX_COMPANY_NAME_LENGTH);
    }

    private void setTradeName(String tradeName) {
        String normalizedTradeName = SupplierDomainValidation.normalize(companyName);
        String capitalized = StringUtils.capitalize(normalizedTradeName);
        this.companyName = SupplierDomainValidation.require_non_blank(capitalized, "Trade name", MAX_TRADE_NAME_LENGTH);
    }

    private void setStreet(String street) {
        String normalizedStreet = SupplierDomainValidation.normalize(companyName);
        String capitalized = StringUtils.capitalize(normalizedStreet);
        this.companyName = SupplierDomainValidation.require_non_blank(capitalized, "Street", MAX_STREET_LENGTH);
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
