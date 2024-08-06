package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tags")
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTag;
    private String tagName;
    public Tags(String tagName) {
        this.tagName = tagName;
    }
    public Tags() {
    }
    public int getIdTag() {
        return idTag;
    }
    public String getTagName() {
        return tagName;
    }


}
