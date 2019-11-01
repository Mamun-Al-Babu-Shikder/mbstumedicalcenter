package com.mcubes.model;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 10/8/2019.
 */
@Entity
public class Message {

    public static final String SEEN = "seen", UNSEEN = "unseen";

    @Id
    @SequenceGenerator(name = "MessageSequence", sequenceName = "MessageSequence", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="MessageSequence")
    private Long id;
    private String date;
    private String sender;
    private String receiver;
    private String patient;
    private String doctor;
    private String subject;
    @Column(length = 1000)
    private String message;
    private String status;

    public Message() {
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
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
        return "Message{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", doctor='" + doctor + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
