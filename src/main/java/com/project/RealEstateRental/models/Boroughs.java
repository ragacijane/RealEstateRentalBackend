package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name="boroughs")
public class Boroughs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBor;
    @Column(name = "borough_name")
    private String boroughName;

    public Boroughs() {
    }
    public Boroughs(String boroughName) {
        this.boroughName = boroughName;
    }
    public long getId() {
        return idBor;
    }
    public String getBoroughName() {
        return boroughName;
    }


}
