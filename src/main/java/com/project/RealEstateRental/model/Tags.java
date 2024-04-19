package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tags")
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdTag;
    @Column(name = "TagName")
    private String TagName;
    public Tags(String tagName) {
        TagName = tagName;
    }
    public Tags() {
    }
    public int getIdTag() {
        return IdTag;
    }
    public String getTagName() {
        return TagName;
    }


}
