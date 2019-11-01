package com.mcubes.model;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 10/12/2019.
 */
@Entity
public class Contact {

    public static final String SEEN = "seen", UNSEEN = "unseen";

    @Id
    @SequenceGenerator(name = "ContactSequence", sequenceName = "ContactSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ContactSequence")
    private Long id;
    private String date;
    private String email;
    private String name;
    @Column(length = 2500)
    private String message;
    private String subject;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", subject='" + subject + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
