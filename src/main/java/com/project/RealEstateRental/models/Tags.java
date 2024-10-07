package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tags")
public class Tags {
    private static int nextId = 1;
    @Id
    private int idTag;
    private String tagName;
    public Tags(String tagName) {
        this.idTag = nextId++;
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
