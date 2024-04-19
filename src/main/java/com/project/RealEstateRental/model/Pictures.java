package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pictures")
public class Pictures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdPicture;
    @Column(name = "Path")
    private String Path;

    public Pictures(String path) {
        Path = path;
    }

    public Pictures() {

    }

    public String getPath() {
        return Path;
    }

    public int getId() {
        return IdPicture;
    }
}
