package com.example.subscriber_bc.dto;

import com.example.subscriber_bc.dto.validation.CreateValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class SubscriberDTO {
    @NotNull(groups = CreateValidationGroup.class, message = "First name is required")
    private String fname;

    @NotNull(groups = CreateValidationGroup.class, message = "Last name is required")
    private String lname;

    @NotNull(groups = CreateValidationGroup.class, message = "Email is required")
    @Email(message = "Email should be valid")
    private String mail;

    @NotNull(groups = CreateValidationGroup.class, message = "Phone is required")
    private String phone;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
