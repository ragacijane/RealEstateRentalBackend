package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "properties")
public class Properties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_property")
    private long idProperty;
    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "idType")
    private Types type;
    @ManyToOne
    @JoinColumn(name = "structure_id", referencedColumnName = "idStructure")
    private Structures structure;
    @Column(name = "rooms")
    private int rooms;
    @Column(name = "square_footage")
    private int squareFootage;
    @Column(name = "bathrooms")
    private int bathrooms;
    @Column(name = "Heating")
    private String Heating;
    @ManyToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "idEquipment")
    private Equipments equipment;
    @Column(name = "status")
    private int status;
    @Column(name = "deposit")
    private int deposit;
    @Column(name = "price")
    private int price;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;

    public Properties(Types type, Structures structure, int rooms, int squareFootage, int bathrooms, String heating, Equipments equipment, int status, int deposit, int price, String title, String description) {
        this.type = type;
        this.structure = structure;
        this.rooms = rooms;
        this.squareFootage = squareFootage;
        this.bathrooms = bathrooms;
        Heating = heating;
        this.equipment = equipment;
        this.status = status;
        this.deposit = deposit;
        this.price = price;
        this.title = title;
        this.description = description;
    }

    public Properties() {
    }

    public long getIdProperty() {
        return idProperty;
    }

    public Types getType() {
        return type;
    }

    public Structures getStructure() {
        return structure;
    }

    public int getRooms() {
        return rooms;
    }

    public int getSquareFootage() {
        return squareFootage;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public String getHeating() {
        return Heating;
    }

    public Equipments getEquipment() {
        return equipment;
    }

    public int getStatus() {
        return status;
    }

    public int getDeposit() {
        return deposit;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
