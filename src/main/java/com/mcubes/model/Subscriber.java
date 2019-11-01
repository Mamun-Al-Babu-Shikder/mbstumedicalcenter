package com.mcubes.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by A.A.MAMUN on 10/26/2019.
 */
@Entity
public class Subscriber {

    @Id
    private String emailAddress;

    public Subscriber(){}

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
