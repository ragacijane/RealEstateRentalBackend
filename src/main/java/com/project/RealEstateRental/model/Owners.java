package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "owners")
public class Owners {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idOwner;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "contract")
    private String contract;

    @Column(name = "street")
    private String street;
    @Column(name = "number")
    private long number;

    @OneToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id_property")
    private Properties property;
    public Owners(String firstName, String lastName, String phone, String contract, String street, long number, Properties property) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.contract = contract;
        this.street = street;
        this.number = number;
        this.property = property;
    }
    public Owners() {
    }
    public long getIdOwner() {
        return idOwner;
    }

    public Properties getProperty() {
        return property;
    }
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
