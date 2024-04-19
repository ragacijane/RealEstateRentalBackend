package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "structures")
public class Structures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdStructure;
    @Column(name = "StructureType")
    private String StructureType;

    public Structures() {
    }

    public Structures(String structureType) {
        StructureType = structureType;
    }

    public int getIdStructure() {
        return IdStructure;
    }

    public String getStructureType() {
        return StructureType;
    }
}
