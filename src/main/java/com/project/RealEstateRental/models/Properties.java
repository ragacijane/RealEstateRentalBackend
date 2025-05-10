package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name = "properties")
public class Properties {

    private static long nextId = 100;
    @Id
    @Column(name = "id_property")
    private long idProperty;

    private String name;
    private String email;
    private String phone;
    private String contract;
    private String street;
    private String number;
    private String moreInfo;
    @ManyToOne
    @JoinColumn(name = "id_type", referencedColumnName = "id_type")
    private Types type;
    @ManyToOne
    @JoinColumn(name = "id_structure", referencedColumnName = "id_structure")
    private Structures structure;
    private String rooms;
    @Column( name="square_footage")
    private Integer squareFootage;
    @ManyToOne
    @JoinColumn(name = "id_borough", referencedColumnName = "id_borough")
    private Boroughs borough;
    private String floor;
    private String bathrooms;
    private String heating;
    @ManyToOne
    @JoinColumn(name = "id_equipment", referencedColumnName = "id_equipment")
    private Equipments equipment;
    private int active;
    private int visible;
    @Column(name = "category")
    private int category;
    private int deposit;
    @Column(name = "price")
    private Integer price;
    private String title;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private String thumbnail;
    public Properties() {}

    public Properties(String name, String email, String phone, String contract, String street, String number,String moreInfo,Types type, Structures structure, String rooms, Integer squareFootage, Boroughs borough, String floor, String bathrooms, String heating, Equipments equipment, int active, int visible, int category, int deposit, Integer price, String title, String description) {
        this.idProperty = nextId++;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.contract = contract;
        this.street = street;
        this.number = number;
        this.moreInfo = moreInfo;
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

    public static void setNextId(long id){
        nextId=id;
    }
    public static long getNextId() {
        return nextId;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

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

    public String getNumber() {
        return number;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

    public void setEmail(String lastName) {
        this.email = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getRooms() {
        return rooms;
    }

    public Integer getSquareFootage() {
        return squareFootage;
    }

    public String getBathrooms() {
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

    public Integer getPrice() {
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

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public void setSquareFootage(Integer squareFootage) {
        this.squareFootage = squareFootage;
    }

    public void setBathrooms(String bathrooms) {
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

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
