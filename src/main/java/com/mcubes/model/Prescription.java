package com.mcubes.model;

import javax.persistence.*;
import javax.validation.constraints.Max;

/**
 * Created by A.A.MAMUN on 9/17/2019.
 */
@Entity
public class Prescription {

    @Id
    @SequenceGenerator(name = "PrescriptionSequence", sequenceName = "PrescriptionSequence", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="PrescriptionSequence")
    private Long id;
    private String date;
    private String dremail;
    private String drname;
    private String drDegree;
    private String drPhone;
    private String pid;
    private String pname;
    private String psex;
    private String page;
    @Column(length = 999999)
    private String body;


    public String getDrDegree() {
        return drDegree;
    }

    public void setDrDegree(String drDegree) {
        this.drDegree = drDegree;
    }

    public String getDrPhone() {
        return drPhone;
    }

    public void setDrPhone(String drPhone) {
        this.drPhone = drPhone;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPsex() {
        return psex;
    }

    public void setPsex(String psex) {
        this.psex = psex;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
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

    public String getDremail() {
        return dremail;
    }

    public void setDremail(String dremail) {
        this.dremail = dremail;
    }

    public String getDrname() {
        return drname;
    }

    public void setDrname(String drname) {
        this.drname = drname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", dremail='" + dremail + '\'' +
                ", drname='" + drname + '\'' +
                ", pid='" + pid + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
