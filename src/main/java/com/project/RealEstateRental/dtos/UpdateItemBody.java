package com.project.RealEstateRental.dtos;


import com.project.RealEstateRental.models.Properties;

public class UpdateItemBody {
    private Properties item;
    private String tagIds;

    public void setItem(Properties item) {
        this.item = item;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public Properties getItem() {
        return item;
    }

    public String getTagIds() {
        return tagIds;
    }
}
