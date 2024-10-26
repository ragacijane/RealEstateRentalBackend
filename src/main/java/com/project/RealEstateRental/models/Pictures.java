package com.project.RealEstateRental.models;

import jakarta.persistence.*;

@Entity
@Table(name = "pictures")
public class Pictures {

    private static long nextId = 1;

    @Id
    private long idPicture;
    private String pictureName;
    private String picturePath;
    private String thumbnailPath;
    private int sequenceNumber;
    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id_property")
    private Properties property;

    protected Pictures() {}

    public Pictures(String pictureName, String picturePath, String thumbnailPath , Properties property, int sequenceNumber) {
        this.idPicture=nextId++;
        this.pictureName = pictureName;
        this.picturePath = picturePath;
        this.thumbnailPath = thumbnailPath;
        this.property = property;
        this.sequenceNumber = sequenceNumber;
    }

    public static void setNextId(long id){
        nextId=id;
    }

    public static long getNextId(){
        return nextId;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public void setThumbnailPath(String path) {
        this.thumbnailPath = path;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setSequenceNumber(int num){ this.sequenceNumber = num;}

    public String getPictureName() {
        return pictureName;
    }

    public Properties getProperty() {
        return property;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public int getSequenceNumber(){return sequenceNumber;}

    public long getId() {
        return idPicture;
    }
}
