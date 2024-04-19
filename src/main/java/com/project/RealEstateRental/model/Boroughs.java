package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name="boroughs")
public class Boroughs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_bor;
    @Column(name = "borough_name")
    private String brobro;

    public Boroughs() {
    }
    public Boroughs(String boroughName) {
        brobro = boroughName;
    }
    public int getId() {
        return id_bor;
    }
    public String getBoroughName() {
        return brobro;
    }


}
