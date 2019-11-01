package com.mcubes.model;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 10/21/2019.
 */
@Entity
public class HealthTipsBook {

    @Id
    @SequenceGenerator(name = "HealthTipsBookSequence", sequenceName = "HealthTipsBookSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HealthTipsBookSequence")
    private Long id;
    private String image;
    private String description;
    private String link;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "HealthTipsBook{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
