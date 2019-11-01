package com.mcubes.model;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 10/20/2019.
 */
@Entity
public class HealthPost {

    @Id
    @SequenceGenerator(name = "HealthPostSequence", sequenceName = "HealthPostSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HealthPostSequence")
    private Long id;
    private String date;
    private Long views;
    private String poster;
    private String image;
    private String title;
    @Column(length = 5500)
    private String body;

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

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "HealthPost{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", views=" + views +
                ", poster='" + poster + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
