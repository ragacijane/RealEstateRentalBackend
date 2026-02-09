package com.project.RealEstateRental.models;

import com.project.RealEstateRental.dtos.CustomerDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customers {

    private static long nextId = 1;

    @Id
    @Column(name = "id_customer")
    private long idCustomer;

    private String firstName;

    private String secondName;

    private String email;

    private String phoneNumber;

    private String clientType;

    @Column(columnDefinition = "TEXT")
    private String demand;

    @Column(columnDefinition = "TEXT")
    private String offers;

    @Column(columnDefinition = "TEXT")
    private String showed;

    @Column(columnDefinition = "TEXT")
    private String pros;

    @Column(columnDefinition = "TEXT")
    private String cons;

    @Column(columnDefinition = "TEXT")
    private String contract;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean isActive;

    public Customers(CustomerDTO dto) {
        if (dto.getIdCustomer() == null) {
            this.idCustomer = nextId++;
        } else {
            this.idCustomer = dto.getIdCustomer();
        }

        this.firstName = dto.getFirstName();
        this.secondName = dto.getSecondName();
        this.email = dto.getEmail();
        this.phoneNumber = dto.getPhoneNumber();
        this.clientType = dto.getClientType();
        this.demand = dto.getDemand();
        this.offers = dto.getOffers();
        this.showed = dto.getShowed();
        this.pros = dto.getPros();
        this.cons = dto.getCons();
        this.contract = dto.getContract();
        this.description = dto.getDescription();
        this.isActive = dto.isActive();
    }

    public void updateFromDto(CustomerDTO dto) {
        this.firstName = dto.getFirstName();
        this.secondName = dto.getSecondName();
        this.email = dto.getEmail();
        this.phoneNumber = dto.getPhoneNumber();
        this.clientType = dto.getClientType();
        this.demand = dto.getDemand();
        this.offers = dto.getOffers();
        this.showed = dto.getShowed();
        this.pros = dto.getPros();
        this.cons = dto.getCons();
        this.contract = dto.getContract();
        this.description = dto.getDescription();
        this.isActive = dto.isActive();
    }


    public static void setNextId(long id){
        nextId=id;
    }
    public static long getNextId() {
        return nextId;
    }

    protected Customers() {
    }

    public long getIdCustomer() {
        return idCustomer;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getClientType() {
        return clientType;
    }

    public String getDemand() {
        return demand;
    }

    public String getOffers() {
        return offers;
    }

    public String getShowed() {
        return showed;
    }

    public String getPros() {
        return pros;
    }

    public String getCons() {
        return cons;
    }

    public String getContract() {
        return contract;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setFirstName(String firstName) {
    }

    public void setSecondName(String secondName) {
    }

    public void setEmail(String email) {
    }

    public void setPhoneNumber(String phoneNumber) {
    }

    public void setClientType(String clientType) {
    }

    public void setDemand(String demand) {
    }

    public void setOffers(String offers) {
    }

    public void setShowed(String showed) {
    }

    public void setPros(String pros) {
    }

    public void setCons(String cons) {
    }

    public void setContract(String contract) {
    }

    public void setDescription(String description) {
    }

    public void setActive(boolean active) {
    }
}
