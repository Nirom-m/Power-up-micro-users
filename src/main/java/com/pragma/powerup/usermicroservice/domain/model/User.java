package com.pragma.powerup.usermicroservice.domain.model;

import java.time.LocalDate;

public class User {
    private Long id;
    private String name;
    private String lastName;
    private String dniNumber;
    private String phone;
    private LocalDate birthDay;
    private String mail;
    private String password;
    private Role Role;

    public User(Long id, String name, String lastName, String dniNumber, String phone, LocalDate birthDay, String mail, String password, Role role) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.dniNumber = dniNumber;
        this.phone = phone;
        this.birthDay = birthDay;
        this.mail = mail;
        this.password = password;
        Role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDniNumber() {
        return dniNumber;
    }

    public void setDniNumber(String dniNumber) {
        this.dniNumber = dniNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public com.pragma.powerup.usermicroservice.domain.model.Role getRole() {
        return Role;
    }

    public void setRole(com.pragma.powerup.usermicroservice.domain.model.Role role) {
        Role = role;
    }
}
