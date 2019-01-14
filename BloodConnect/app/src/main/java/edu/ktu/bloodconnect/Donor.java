package edu.ktu.bloodconnect;

import java.io.Serializable;

public class Donor implements Serializable {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String bloodType;
    private int age;
    private String email;
    private String city;

    public Donor(){

    }

    public Donor(String firstName, String lastName, String phoneNumber, String bloodType, int age, String email, String city) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.bloodType = bloodType;
        this.age = age;
        this.email = email;
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBloodType() {
        return bloodType;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }
}
