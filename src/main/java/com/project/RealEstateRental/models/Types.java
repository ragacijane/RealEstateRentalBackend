package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name = "types")
public class Types {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type")
    private int idType;

    private String typeName;

    public int getIdType() {
        return idType;
    }

    public String getTypeName() {
        return typeName;
    }

    public Types(String typeName) {
        this.typeName = typeName;
    }

    public Types() {
    }
}
