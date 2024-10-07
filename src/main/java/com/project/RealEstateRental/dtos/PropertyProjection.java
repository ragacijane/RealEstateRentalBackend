package com.project.RealEstateRental.dtos;

import com.project.RealEstateRental.models.Boroughs;
import com.project.RealEstateRental.models.Equipments;
import com.project.RealEstateRental.models.Structures;
import com.project.RealEstateRental.models.Types;

public interface PropertyProjection {
    Types getType();
    Structures getStructure();
    Boroughs getBorough();
    Equipments getEquipment();

    String getRooms();
    String getSquareFootage();
    String getFloor();
    String getBathrooms();
    String getHeating();
    int getActive();
    int getVisible();
    int getCategory();
    int getDeposit();
    String getPrice();
    String getTitle();
    String getDescription();
    String getThumbnail();
}
