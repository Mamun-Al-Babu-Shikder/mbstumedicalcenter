package com.mcubes.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by A.A.MAMUN on 9/12/2019.
 */
@Entity
public class Doctor {

    @Id
    private String email;
    private String password;
    private String name;
    private String degree;
    private String phone;

    public Doctor() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", degree='" + degree + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

