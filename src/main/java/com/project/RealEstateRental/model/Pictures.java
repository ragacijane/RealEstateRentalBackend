package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pictures")
public class Pictures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdPicture;
    @Column(name = "picture_path")
    private String picturePath;
    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id_property")
    private Properties property;

    public Pictures(String path) {
        picturePath = path;
    }

    public Pictures() {

    }

    public String getPicturePath() {
        return picturePath;
    }

    public int getId() {
        return IdPicture;
    }
}
