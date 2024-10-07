package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name = "structures")
public class Structures {

    private static int nextId = 1;

    @Id
    @Column(name = "id_structure")
    private int idStructure;
    private String structureName;

    protected Structures() {
    }
    public Structures(String structureName) {

        this.idStructure=nextId++; this.structureName = structureName;
    }

    public int getIdStructure() {
        return idStructure;
    }

    public String getStructureName() {
        return structureName;
    }
}
