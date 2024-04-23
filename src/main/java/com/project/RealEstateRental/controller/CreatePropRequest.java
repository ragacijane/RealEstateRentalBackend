package com.project.RealEstateRental.controller;

public class CreatePropRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String contract;
    private String street;
    private long number;
    private int typeId;
    private int structureId;
    private int rooms;
    private int squareFootage;
    private int bathrooms;
    private String heating;
    private int equipmentId;
    private int boroughId;
    private float floor;
    private int status;
    private int deposit;
    private int price;
    private String title;
    private String description;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public float getFloor() {
        return floor;
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
