package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name = "properties")
public class Properties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_property")
    private long idProperty;
    @ManyToOne
    @JoinColumn(name = "id_type", referencedColumnName = "id_type")
    private Types type;
    @ManyToOne
    @JoinColumn(name = "id_structure", referencedColumnName = "id_structure")
    private Structures structure;
    private int rooms;
    private int squareFootage;
    @ManyToOne
    @JoinColumn(name = "id_borough", referencedColumnName = "id_borough")
    private Boroughs borough;
    private String floor;
    private int bathrooms;
    private String heating;
    @ManyToOne
    @JoinColumn(name = "id_equipment", referencedColumnName = "id_equipment")
    private Equipments equipment;
    private int active;
    private int visible;
    @Column(name = "category")
    private int category;
    private int deposit;
    private int price;
    private String title;
    private String description;
    private String thumbnail;
    public Properties() {}

    public Properties(Types type, Structures structure, int rooms, int squareFootage, Boroughs borough, String floor, int bathrooms, String heating, Equipments equipment, int active, int visible, int category, int deposit, int price, String title, String description) {
        this.type = type;
        this.structure = structure;
        this.rooms = rooms;
        this.squareFootage = squareFootage;
        this.borough = borough;
        this.floor = floor;
        this.bathrooms = bathrooms;
        this.heating = heating;
        this.equipment = equipment;
        this.active = active;
        this.visible = visible;
        this.category = category;
        this.deposit = deposit;
        this.price = price;
        this.title = title;
        this.description = description;
        this.thumbnail=null;
    }

    public String getThumbnail() {
        return thumbnail != null ? thumbnail : "";
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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
    public String getFloor() {
        return floor;
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

    public void setFloor(String floor) {this.floor = floor;}

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
