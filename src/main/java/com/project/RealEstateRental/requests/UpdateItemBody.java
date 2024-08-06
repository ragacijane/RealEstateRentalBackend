package com.project.RealEstateRental.requests;

import com.project.RealEstateRental.models.Owners;

public class UpdateItemBody {
    private Owners item;
    private String tagIds;

    public void setItem(Owners item) {
        this.item = item;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public Owners getItem() {
        return item;
    }

    public String getTagIds() {
        return tagIds;
    }
}
