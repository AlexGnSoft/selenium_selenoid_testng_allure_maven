package com.carespeak.domain.entities.program;

import com.carespeak.domain.entities.common.Sex;

/**
 * Patient represents patients for some program
 */
public class Patient {

    private String cellPhone;
    private String timezone;

    private String firstName;
    private String lastName;
    private String email;

    private Sex sex;

    public Patient() {

    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "cellPhone='" + cellPhone + '\'' +
                ", timezone='" + timezone + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                '}';
    }
}
