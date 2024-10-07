package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name="equipments")
public class Equipments {
    private static int nextId = 1;


    @Id
    @Column(name = "id_equipment")
    private int idEquipment;
    private String equipmentType;

    protected Equipments() {
    }
    public Equipments(String equipmentType) {
        this.idEquipment=nextId++;
        this.equipmentType = equipmentType;
    }
    public int getIdEquipment() {
        return idEquipment;
    }
    public String getEquipmentType() {
        return equipmentType;
    }
}
