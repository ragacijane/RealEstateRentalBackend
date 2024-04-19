package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "properties")
public class Properties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long IdProperty;
    @ManyToOne
    @JoinColumn(name = "Type", referencedColumnName = "IdType")
    private Types Type;
    @ManyToOne
    @JoinColumn(name = "Structure", referencedColumnName = "IdStructure")
    private Structures Structure;
    @Column(name = "Rooms")
    private int Rooms;
    @Column(name = "SquareFootage")
    private int SquareFootage;
    @Column(name = "Bathrooms")
    private int Bathrooms;
    @Column(name = "Heating")
    private String Heating;
    @ManyToOne
    @JoinColumn(name = "Equipment", referencedColumnName = "IdEquipment")
    private Equipments Equipment;
    @Column(name = "Status")
    private int Status;
    @Column(name = "Deposit")
    private int Deposit;
    @Column(name = "Price")
    private int Price;
    @Column(name = "Title")
    private String Title;
    @Column(name = "Description")
    private String Description;

    public Properties(Types type, Structures structure, int rooms, int squareFootage, int bathrooms, String heating, Equipments equipment, int status, int deposit, int price, String title, String description) {
        Type = type;
        Structure = structure;
        Rooms = rooms;
        SquareFootage = squareFootage;
        Bathrooms = bathrooms;
        Heating = heating;
        Equipment = equipment;
        Status = status;
        Deposit = deposit;
        Price = price;
        Title = title;
        Description = description;
    }

    public Properties() {
    }

    public long getIdProperty() {
        return IdProperty;
    }

    public Types getType() {
        return Type;
    }

    public Structures getStructure() {
        return Structure;
    }

    public int getRooms() {
        return Rooms;
    }

    public int getSquareFootage() {
        return SquareFootage;
    }

    public int getBathrooms() {
        return Bathrooms;
    }

    public String getHeating() {
        return Heating;
    }

    public Equipments getEquipment() {
        return Equipment;
    }

    public int getStatus() {
        return Status;
    }

    public int getDeposit() {
        return Deposit;
    }

    public int getPrice() {
        return Price;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }
}
