package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    private static int nextId = 1;

    @Id
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public User(String username, String password) {
        this.id=nextId++;
        this.username = username;
        this.password = password;
    }

    protected User() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String apolone) {
    }

    public void setPassword(String encode) {
    }
}
