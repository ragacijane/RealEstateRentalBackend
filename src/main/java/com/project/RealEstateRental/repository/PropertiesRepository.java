package com.project.RealEstateRental.repository;

import com.project.RealEstateRental.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//TODO Override delete to set invisible :)
public interface PropertiesRepository extends JpaRepository<Properties,Integer> {
    @Query("SELECT p FROM Properties p " +
            "WHERE (:type IS NULL OR p.type = :type) AND " +
            "(:structure IS NULL OR p.structure = :structure) AND " +
            "(:minRooms IS NULL OR p.rooms >= :minRooms) AND " +
            "(:maxRooms IS NULL OR p.rooms <= :maxRooms) AND " +
            "(:squareFootage IS NULL OR p.squareFootage = :squareFootage) AND " +
            "(:bathrooms IS NULL OR p.bathrooms = :bathrooms) AND " +
            "(:heating IS NULL OR p.heating = :heating) AND " +
            "(:equipment IS NULL OR p.equipment = :equipment) AND " +
            "(:borough IS NULL OR p.borough = :borough) AND " +
            "(:floor IS NULL OR p.floor = :floor) AND " +
            "(:status IS NULL OR p.status = :status) AND " +
            "(:deposit IS NULL OR p.deposit = :deposit) AND " +
            "(:price IS NULL OR p.price = :price) AND " +
            "(:title IS NULL OR p.title = :title) AND " +
            "(:description IS NULL OR p.description = :description) AND " +
            "(:tagIds IS NULL OR (SELECT COUNT(pt) FROM Property_tags pt WHERE pt.property = p AND pt.tag.idTag IN (:tagIds)) = :numTags)")
    List<Properties> findByFilter(
            @Param("type") Types type,
            @Param("structure") Structures structure,
            @Param("minRooms") Integer minRooms,
            @Param("maxRooms") Integer maxRooms,
            @Param("squareFootage") Integer squareFootage,
            @Param("bathrooms") Integer bathrooms,
            @Param("heating") String heating,
            @Param("equipment") Equipments equipment,
            @Param("borough") Boroughs borough,
            @Param("floor") Float floor,
            @Param("status") Integer status,
            @Param("deposit") Integer deposit,
            @Param("price") Integer price,
            @Param("title") String title,
            @Param("description") String description,
            @Param("tagIds") List<Long> tagIds,
            @Param("numTags") Long numTags);
}
