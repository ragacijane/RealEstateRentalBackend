package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name = "structures")
public class Structures {

    private static int nextId = 1;

    @Id
    @Column(name = "id_structure")
    private int idStructure;
    private String structureType;

    protected Structures() {
    }
    public Structures(String structureType) {

        this.idStructure=nextId++; this.structureType = structureType;
    }

    public int getIdStructure() {
        return idStructure;
    }

    public String getStructureType() {
        return structureType;
    }
}
