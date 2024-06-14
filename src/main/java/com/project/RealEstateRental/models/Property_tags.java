package com.project.RealEstateRental.models;

import jakarta.persistence.*;
@Entity
@Table(name = "property_tags")
public class Property_tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id_property")
    private Properties property;

    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "idTag")
    private Tags tag;

    public Property_tags() {
    }

    public Property_tags(Properties property, Tags tag) {
        this.property = property;
        this.tag = tag;
    }

    public long getId() {
        return id;
    }

    public Properties getProperty() {
        return property;
    }

    public Tags getTag() {
        return tag;
    }
}
