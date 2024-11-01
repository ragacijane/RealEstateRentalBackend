package com.project.RealEstateRental.requests;

public class PatchPropertyBody {
    private Integer active;
    private Integer visible;


    public Integer getActive() {
        return active;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }
}
