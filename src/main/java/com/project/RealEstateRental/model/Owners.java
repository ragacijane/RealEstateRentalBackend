package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "owners")
public class Owners {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "FirstName")
    private String FirstName;
    @Column(name = "LastName")
    private String LastName;
    @Column(name = "Phone")
    private String Phone;
    @Column(name = "Contract")
    private String Contract;
    @ManyToOne
    @JoinColumn(name = "Borough", referencedColumnName = "id_bor")
    private Boroughs Borough;
    @Column(name = "Street")
    private String Street;
    @Column(name = "Number")
    private long Number;
    @Column(name = "Floor")
    private float Floor;

    public Owners(String firstName, String lastName, String phone, String contract, Boroughs borough, String street, long number, float floor) {
        FirstName = firstName;
        LastName = lastName;
        Phone = phone;
        Contract = contract;
        Borough = borough;
        Street = street;
        Number = number;
        Floor = floor;
    }

    public Owners() {
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getPhone() {
        return Phone;
    }

    public String getContract() {
        return Contract;
    }

    public Boroughs getBorough() {
        return Borough;
    }

    public String getStreet() {
        return Street;
    }

    public long getNumber() {
        return Number;
    }

    public float getFloor() {
        return Floor;
    }
}
