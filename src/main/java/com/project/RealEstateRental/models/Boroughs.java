package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name="boroughs")
public class Boroughs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_borough")
    private int idBorough;
    private String boroughName;

    public Boroughs() {
    }
    public Boroughs(String boroughName) {
        this.boroughName = boroughName;
    }
    public int getIdBorough() {
        return idBorough;
    }
    public String getBoroughName() {
        return boroughName;
    }
}
