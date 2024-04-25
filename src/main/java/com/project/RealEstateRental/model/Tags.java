package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tags")
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTag;
    @Column(name = "tag_name")
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
