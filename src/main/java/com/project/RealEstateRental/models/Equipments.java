package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name="equipments")
public class Equipments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipment")
    private int idEquipment;
    private String equipmentType;

    public Equipments() {
    }
    public Equipments(String equipmentType) {
        this.equipmentType = equipmentType;
    }
    public int getIdEquipment() {
        return idEquipment;
    }
    public String getEquipmentType() {
        return equipmentType;
    }
}
