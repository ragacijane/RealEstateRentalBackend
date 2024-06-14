package com.project.RealEstateRental.controllers;

public class PropertyBody {
    private String name;
    private String email;
    private String phone;
    private String contract;
    private String street;
    private long number;
    private String moreInfo;
    //***************************
    private int typeId;
    private int structureId;
    private int rooms;
    private int squareFootage;
    private int bathrooms;
    private String heating;
    private int equipmentId;
    private int boroughId;
    private String floor;
    private int active;
    private int visible;
    private int deposit;
    private int category;
    private int price;
    private String title;
    private String description;
    private String tagIds;
    public int getCategory() {
        return category;
    }
    public int getVisible() {
        return visible;
    }
    public String getTagIds(){return tagIds;}
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getContract() {
        return contract;
    }
    public String getStreet() {
        return street;
    }
    public long getNumber() {
        return number;
    }
    public int getTypeId() {
        return typeId;
    }
    public int getStructureId() {
        return structureId;
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
    public int getEquipmentId() {
        return equipmentId;
    }
    public int getBoroughId() {
        return boroughId;
    }
    public String getFloor() { return floor; }
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
    public String getDescription() {
        return description;
    }
    public String getMoreInfo() { return moreInfo;
    }
}
