package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pictures")
public class Pictures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPicture;
    @Column(name="picture_name")
    private String pictureName;
    @Column(name = "picture_path")
    private String picturePath;
    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id_property")
    private Properties property;

    public Pictures() {

    }

    public Pictures(String pictureName, String picturePath, Properties property) {
        this.pictureName = pictureName;
        this.picturePath = picturePath;
        this.property = property;
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
