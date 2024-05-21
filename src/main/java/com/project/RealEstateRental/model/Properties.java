package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "properties")
public class Properties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_property")
    private int idProperty;
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
    private String heating;
    @ManyToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "idEquipment")
    private Equipments equipment;

    @ManyToOne
    @JoinColumn(name = "borough_id", referencedColumnName = "idBor")
    private Boroughs borough;
    @Column(name = "floor")
    private float floor;
    @Column(name = "active")
    private int active;
    @Column(name = "visible")
    private int visible;
    @Column(name = "category")
    private int category;

    public Properties(Types type, Structures structure, int rooms, int squareFootage, int bathrooms, String heating, Equipments equipment, Boroughs borough, float floor, int active, int visible, int category, int deposit, int price, String title, String description) {
        this.type = type;
        this.structure = structure;
        this.rooms = rooms;
        this.squareFootage = squareFootage;
        this.bathrooms = bathrooms;
        this.heating = heating;
        this.equipment = equipment;
        this.borough = borough;
        this.floor = floor;
        this.active = active;
        this.visible = visible;
        this.category = category;
        this.deposit = deposit;
        this.price = price;
        this.title = title;
        this.description = description;
    }

    @Column(name = "deposit")
    private int deposit;
    @Column(name = "price")
    private int price;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;

    public Properties(Types type, Structures structure, int rooms, int squareFootage, Boroughs borough,
                      float floor, int bathrooms, String heating, Equipments equipment, int active, int visible,int category, int deposit, int price, String title, String description) {
        this.type = type;
        this.structure = structure;
        this.rooms = rooms;
        this.squareFootage = squareFootage;
        this.bathrooms = bathrooms;
        this.heating = heating;
        this.equipment = equipment;
        this.borough=borough;
        this.floor=floor;
        this.active = active;
        this.visible = visible;
        this.category=category;
        this.deposit = deposit;
        this.price = price;
        this.title = title;
        this.description = description;
    }

    public Boroughs getBorough() {
        return borough;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public float getFloor() {
        return floor;
    }

    public Properties() {
    }

    public int getIdProperty() {
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
        return heating;
    }

    public Equipments getEquipment() {
        return equipment;
    }

    public int getActive() {
        return active;
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

    public int getVisible() {
        return visible;
    }

    public String getDescription() {
        return description;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public void setStructure(Structures structure) {
        this.structure = structure;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public void setSquareFootage(int squareFootage) {
        this.squareFootage = squareFootage;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public void setHeating(String heating) {
        this.heating = heating;
    }

    public void setEquipment(Equipments equipment) {
        this.equipment = equipment;
    }

    public void setBorough(Boroughs borough) {
        this.borough = borough;
    }

    public void setFloor(float floor) {
        this.floor = floor;
    }

    public void setActive(int status) {
        this.active = status;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
