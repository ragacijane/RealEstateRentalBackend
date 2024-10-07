package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name="equipments")
public class Equipments {
    private static int nextId = 1;


    @Id
    @Column(name = "id_equipment")
    private int idEquipment;
    private String equipmentName;

    protected Equipments() {
    }
    public Equipments(String equipmentName) {
        this.idEquipment=nextId++;
        this.equipmentName = equipmentName;
    }
    public int getIdEquipment() {
        return idEquipment;
    }
    public String getEquipmentName() {
        return equipmentName;
    }
}
