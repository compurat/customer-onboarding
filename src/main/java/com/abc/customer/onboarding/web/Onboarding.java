package com.abc.customer.onboarding.web;

import com.abc.customer.onboarding.database.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class Onboarding {

    @NotNull(message = "Copy of ID is required")
    private CopyOfId copyOfId;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birth;

    @Pattern(regexp = "^0\\d{1,3}-?\\d{6,7}$", message = "Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^0\\d{1}-?\\d{6,9}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Email address is required")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email address must be valid"
    )
    private String mailAddress;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    @NotBlank(message = "Residential address is required")
    @Size(min = 10, max = 200, message = "Address must be between 10 and 200 characters")
    private String residentialAddress;

    @NotBlank(message = "Social security number is required")

    private String socialSecurityNumber;


    public CopyOfId getCopyOfId() {
        return copyOfId;
    }

    public void setCopyOfId(CopyOfId copyOfId) {
        this.copyOfId = copyOfId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }
}
