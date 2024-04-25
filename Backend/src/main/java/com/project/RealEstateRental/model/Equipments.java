package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name="equipments")
public class Equipments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEquipment;
    @Column(name = "equipment_type")
    private String equipmentType;

    public Equipments() {
    }
    public Equipments(String equipmentType) {
        this.equipmentType = equipmentType;
    }
    public long getId() {
        return idEquipment;
    }
    public String getEquipmentType() {
        return equipmentType;
    }
}
