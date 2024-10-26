package com.project.RealEstateRental.models;

import jakarta.persistence.*;
@Entity
@Table(name = "property_tags")
public class Property_tags {

    private static long nextId = 1;

    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id_property")
    private Properties property;

    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "idTag")
    private Tags tag;

    protected Property_tags() {
    }

    public Property_tags(Properties property, Tags tag) {
        this.id=nextId++;
        this.property = property;
        this.tag = tag;
    }
    public static void setNextId(long num){ nextId = num;}
    public static long getNextId(){return nextId;}
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
