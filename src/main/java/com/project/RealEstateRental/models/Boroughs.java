package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name="boroughs")
public class Boroughs {
    private static int nextId = 1;

    @Id
    @Column(name = "id_borough")
    private int idBorough;
    private String boroughName;

    public Boroughs(String boroughName) {
        this.idBorough = nextId++;
        this.boroughName = boroughName;
    }

    protected Boroughs() {
    }
    public int getIdBorough() {
        return idBorough;
    }
    public String getBoroughName() {
        return boroughName;
    }
}
