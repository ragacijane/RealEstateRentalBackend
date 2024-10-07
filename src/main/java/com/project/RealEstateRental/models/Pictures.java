package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name = "pictures")
public class Pictures {

    private static int nextId = 1;

    @Id
    private int idPicture;
    private String pictureName;
    private String picturePath;
    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id_property")
    private Properties property;

    protected Pictures() {}

    public Pictures(String pictureName, String picturePath, Properties property) {
        this.idPicture=nextId++;
        this.pictureName = pictureName;
        this.picturePath = picturePath;
        this.property = property;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getPictureName() {
        return pictureName;
    }

    public Properties getProperty() {
        return property;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public int getId() {
        return idPicture;
    }
}
