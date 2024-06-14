package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name = "structures")
public class Structures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStructure;
    @Column(name = "structure_type")
    private String structureType;

    public Structures() {
    }

    public Structures(String structureType) {
        this.structureType = structureType;
    }

    public int getIdStructure() {
        return idStructure;
    }

    public String getStructureType() {
        return structureType;
    }
}
