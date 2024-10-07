package com.project.RealEstateRental.dtos;

public interface PropertyProjection {
    String getTitle();
    String getRooms();
    String getSquareFootage();
    String getFloor();
    String getBathrooms();
    String getHeating();
    int getDeposit();
    String getPrice();
    String getDescription();
    String getThumbnail();
    int getActive();
    int getVisible();
    int getCategory();

    TypeProjection getType();
    StructureProjection getStructure();
    BoroughProjection getBorough();
    EquipmentProjection getEquipment();

    interface TypeProjection {
        Integer getIdType();
        String getTypeName();
    }

    interface StructureProjection {
        Integer getIdStructure();
        String getStructureName();
    }

    interface BoroughProjection {
        Integer getIdBorough();
        String getBoroughName();
    }

    interface EquipmentProjection {
        Integer getIdEquipment();
        String getEquipmentName();
    }
}
