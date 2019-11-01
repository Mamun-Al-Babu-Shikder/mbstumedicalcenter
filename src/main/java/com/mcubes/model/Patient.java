package com.mcubes.model;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 9/7/2019.
 */
@Entity
public class Patient {


    @Id
    @SequenceGenerator(name = "PatientSequence", sequenceName = "PatientSequence", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PatientSequence")
    private Long id;
    @Column(unique =true)
    private String pid;
    @Column(length = 5)
    private Integer yyyy;
    @Column(length = 3)
    private Integer mm;
    @Column(length = 3)
    private Integer dd;
    private String pname;
    private String ptype;
    private String pfather;
    private String pmother;
    private String paddress;
    private String psex;
    private String pdob;
    private String pweight;
    private String pheight;
    private String pdept;
    private String pblood;
    private String pcontact;
    private String pmaritalstatus;

    public Patient(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYyyy() {
        return yyyy;
    }

    public void setYyyy(Integer yyyy) {
        this.yyyy = yyyy;
    }

    public Integer getMm() {
        return mm;
    }

    public void setMm(Integer mm) {
        this.mm = mm;
    }

    public Integer getDd() {
        return dd;
    }

    public void setDd(Integer dd) {
        this.dd = dd;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getPfather() {
        return pfather;
    }

    public void setPfather(String pfather) {
        this.pfather = pfather;
    }

    public String getPmother() {
        return pmother;
    }

    public void setPmother(String pmother) {
        this.pmother = pmother;
    }

    public String getPaddress() {
        return paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }

    public String getPsex() {
        return psex;
    }

    public void setPsex(String psex) {
        this.psex = psex;
    }

    public String getPdob() {
        return pdob;
    }

    public void setPdob(String pdob) {
        this.pdob = pdob;
    }

    public String getPweight() {
        return pweight;
    }

    public void setPweight(String pweight) {
        this.pweight = pweight;
    }

    public String getPheight() {
        return pheight;
    }

    public void setPheight(String pheight) {
        this.pheight = pheight;
    }

    public String getPdept() {
        return pdept;
    }

    public void setPdept(String pdept) {
        this.pdept = pdept;
    }

    public String getPblood() {
        return pblood;
    }

    public void setPblood(String pblood) {
        this.pblood = pblood;
    }

    public String getPcontact() {
        return pcontact;
    }

    public void setPcontact(String pcontact) {
        this.pcontact = pcontact;
    }

    public String getPmaritalstatus() {
        return pmaritalstatus;
    }

    public void setPmaritalstatus(String pmaritalstatus) {
        this.pmaritalstatus = pmaritalstatus;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", yyyy=" + yyyy +
                ", mm=" + mm +
                ", dd=" + dd +
                ", pname='" + pname + '\'' +
                ", ptype='" + ptype + '\'' +
                ", pfather='" + pfather + '\'' +
                ", pmother='" + pmother + '\'' +
                ", paddress='" + paddress + '\'' +
                ", psex='" + psex + '\'' +
                ", pdob='" + pdob + '\'' +
                ", pweight='" + pweight + '\'' +
                ", pheight='" + pheight + '\'' +
                ", pdept='" + pdept + '\'' +
                ", pblood='" + pblood + '\'' +
                ", pcontact='" + pcontact + '\'' +
                ", pmaritalstatus='" + pmaritalstatus + '\'' +
                '}';
    }
}
