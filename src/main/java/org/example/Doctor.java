package org.example;

import java.util.Date;
public class Doctor {
    private int id;
    private String lastName, firstName, middleName, position, specialization;
    private Date birthDate;

    public Doctor(int id, String lastName, String firstName, String middleName, Date birthDate, String position, String specialization) {
        this.id=id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.specialization = specialization;
        this.position = position;
    }
    public int getId() {
        return id;
    }
    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }


    public Date getBirthDate() {
        return birthDate;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getPosition() {
        return position;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
