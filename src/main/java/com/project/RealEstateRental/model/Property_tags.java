package com.project.RealEstateRental.model;

import jakarta.persistence.*;
@Entity
@Table(name = "property_tags")
public class Property_tags {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne
    @JoinColumn(name = "IdProp", referencedColumnName = "IdProperty")
    private Properties Property;

    @ManyToOne
    @JoinColumn(name = "IdTag", referencedColumnName = "IdTag")
    private Tags Tag;

    public Property_tags() {
    }

    public Property_tags(Properties property, Tags tag) {
        Property = property;
        Tag = tag;
    }

    public long getId() {
        return Id;
    }

    public Properties getProperty() {
        return Property;
    }

    public Tags getTag() {
        return Tag;
    }
}
