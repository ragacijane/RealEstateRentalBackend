package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name="boroughs")
public class Boroughs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBor;
    @Column(name = "borough_name")
    private String boroughName;

    public Boroughs() {
    }
    public Boroughs(String boroughName) {
        this.boroughName = boroughName;
    }
    public int getId() {
        return idBor;
    }
    public String getBoroughName() {
        return boroughName;
    }


}
