package com.github.reenatobruno.parts_api.entity;

import com.github.reenatobruno.parts_api.util.DomainValidation;
import com.github.reenatobruno.parts_api.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private static final int MAX_ZIP_LENGTH = 9;
    private static final int MAX_STREET_LENGTH = 150;
    private static final int MAX_NUMBER_LENGTH = 10;
    private static final int MAX_COMPLEMENT_LENGTH = 50;
    private static final int MAX_DISTRICT_LENGTH = 50;
    private static final int MAX_CITY_LENGTH = 50;
    private static final int MAX_STATE_LENGTH = 2;

    @Column(name = "zip_code", nullable = false, length = MAX_ZIP_LENGTH)
    private String zipCode;

    @Column(nullable = false, length = MAX_STREET_LENGTH)
    private String street;

    @Column(nullable = false, length = MAX_NUMBER_LENGTH)
    private String number;

    @Column(name = "address_complement,", length = MAX_COMPLEMENT_LENGTH)
    private String complement;

    @Column(nullable = false, length = MAX_DISTRICT_LENGTH)
    private String district;

    @Column(nullable = false, length = MAX_CITY_LENGTH)
    private String city;

    @Column(nullable = false, length = MAX_STATE_LENGTH)
    private String state;

    protected Address () {};

    public Address(String zipCode, String street, String number, String complement, String district, String city, String state) {
        setZipCode(zipCode);
        setStreet(street);
        setNumber(number);
        setComplement(complement);
        setDistrict(district);
        setCity(city);
        setState(state);
    }

    private void setZipCode(String zipCode) {
        String stripZip = DomainValidation.normalize(zipCode);
        String filterZipCharacters = DomainValidation.filterZipAndPhoneCharacters(stripZip);
        this.zipCode = DomainValidation.requireNonBlank(filterZipCharacters, "Zip", MAX_ZIP_LENGTH);
    }

    private void setStreet(String street) {
        String stripStreet = DomainValidation.normalize(street);
        String capitalized = StringUtils.capitalize(stripStreet);
        this.street = DomainValidation.requireNonBlank(capitalized, "Street", MAX_STREET_LENGTH);
    }

    private void setNumber(String number) {
        String stripNumber = DomainValidation.normalize(number);
        String filterNumberCharacters = DomainValidation.sanitizeText(stripNumber);
        String upperCase = DomainValidation.upperCase(filterNumberCharacters);
        this.number = DomainValidation.requireNonBlank(upperCase, "Number", MAX_NUMBER_LENGTH);
    }

    private void setComplement(String complement) {
        String stripComplement = DomainValidation.normalize(complement);
        String filterComplementCharacters = DomainValidation.sanitizeText(stripComplement);
        String upperCase = DomainValidation.upperCase(filterComplementCharacters);
        this.complement = DomainValidation.requireNonBlankIfPresent(upperCase, "Complement", MAX_COMPLEMENT_LENGTH);
    }

    private void setDistrict(String district) {
        String stripDistrict = DomainValidation.normalize(district);
        String capitalized = StringUtils.capitalize(stripDistrict);
        this.district = DomainValidation.requireNonBlank(capitalized, "District", MAX_DISTRICT_LENGTH);
    }

    private void setCity(String city) {
        String stripCity = DomainValidation.normalize(city);
        String capitalized = StringUtils.capitalize(stripCity);
        this.city = DomainValidation.requireNonBlank(capitalized, "City", MAX_CITY_LENGTH);
    }

    private void setState(String state) {
        String stripState = DomainValidation.normalize(state);
        String upperCase = DomainValidation.upperCase(stripState);
        this.state = DomainValidation.requireNonBlank(upperCase, "State", MAX_STATE_LENGTH);
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

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }
}
