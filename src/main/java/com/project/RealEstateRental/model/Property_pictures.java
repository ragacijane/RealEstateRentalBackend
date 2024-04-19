package com.project.RealEstateRental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "property_pictures")
public class Property_pictures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne
    @JoinColumn(name = "IdProperty", referencedColumnName = "IdProperty")
    private Properties Property;

    @ManyToOne
    @JoinColumn(name = "IdPicture", referencedColumnName = "IdPicture")
    private Pictures Picture;

    public Property_pictures(Properties property, Pictures picture) {
        Property = property;
        Picture = picture;
    }

    public Property_pictures() {

    }

    public long getId() {
        return Id;
    }

    public Properties getProperty() {
        return Property;
    }

    public Pictures getPicture() {
        return Picture;
    }
}
