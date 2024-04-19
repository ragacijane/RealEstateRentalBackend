package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "types")
public class Types {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdType;

    @Column(name = "TypeName")
    private String TypeName;

    public int getIdType() {
        return IdType;
    }

    public String getTypeName() {
        return TypeName;
    }

    public Types(String typeName) {
        TypeName = typeName;
    }

    public Types() {
    }
}
