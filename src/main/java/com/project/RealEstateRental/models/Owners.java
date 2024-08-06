package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name = "owners")
public class Owners {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idOwner;
    private String name;
    private String email;
    private String phone;
    private String contract;
    private String street;
    private long number;
    private String moreInfo;

    @OneToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id_property")
    private Properties property;
    public Owners(String name, String email, String phone, String contract, String street, long number,String moreInfo, Properties property) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.contract = contract;
        this.street = street;
        this.number = number;
        this.moreInfo = moreInfo;
        this.property = property;
    }
    public Owners() {
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public long getIdOwner() {
        return idOwner;
    }

    public Properties getProperty() {
        return property;
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

    public long getNumber() {
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

    public void setNumber(long number) {
        this.number = number;
    }

    public void setProperty(Properties property) {
        this.property = property;
    }
}
