package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name="enquipments")
public class Equipments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdEquipment;
    @Column(name = "EquipmentType")
    private String EquipmentType;

    public Equipments() {
    }
    public Equipments(String equipmentType) {
        EquipmentType = equipmentType;
    }
    public int getId() {
        return IdEquipment;
    }
    public String getEquipmentType() {
        return EquipmentType;
    }
}
