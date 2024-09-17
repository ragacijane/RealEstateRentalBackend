package com.project.RealEstateRental.repositories;

import com.project.RealEstateRental.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface PropertiesRepository extends JpaRepository<Properties,Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Properties p SET p.active = CASE WHEN p.active = 1 THEN 0 ELSE 1 END WHERE p.id = :id")
    int toggleActive(@Param("id") Integer id);

    // Add toggle functionality for the 'visible' field (if needed)
    @Modifying
    @Transactional
    @Query("UPDATE Properties p SET p.visible = CASE WHEN p.visible = 1 THEN 0 ELSE 1 END WHERE p.id = :id")
    int toggleVisible(@Param("id") Integer id);
    @Transactional
    @Query("SELECT p FROM Properties p " +
            "WHERE (:type IS NULL OR p.type = :type) AND " +
            "(:structure IS NULL OR p.structure = :structure) AND " +
            "(:rooms IS NULL OR p.rooms = :rooms) AND " +
            "(:squareFootage IS NULL OR p.squareFootage = :squareFootage) AND " +
            "(:bathrooms IS NULL OR p.bathrooms = :bathrooms) AND " +
            "(:heating IS NULL OR p.heating = :heating) AND " +
            "(:equipment IS NULL OR p.equipment = :equipment) AND " +
            "(:borough IS NULL OR p.borough = :borough) AND " +
            "(:floor IS NULL OR p.floor = :floor) AND " +
            "(:active IS NULL OR p.active = :active) AND " +
            "(:visible IS NULL OR p.visible = :visible) AND " +
            "(:category IS NULL OR p.category = :category) AND " +
            "(:deposit IS NULL OR p.deposit = :deposit) AND " +
            "(:price IS NULL OR p.price = :price) AND " +
            "(:title IS NULL OR p.title = :title) AND " +
            "(:description IS NULL OR p.description = :description) AND " +
            "(:tagIds IS NULL OR (SELECT COUNT(pt.id) FROM Property_tags pt WHERE pt.property = p AND pt.tag.idTag IN (:tagIds)) = :numTags)")
    List<Properties> findByFilter(
            @Param("type") Types type,
            @Param("structure") Structures structure,
            @Param("rooms") Integer rooms,
            @Param("squareFootage") Integer squareFootage,
            @Param("bathrooms") Integer bathrooms,
            @Param("heating") String heating,
            @Param("equipment") Equipments equipment,
            @Param("borough") Boroughs borough,
            @Param("floor") String floor,
            @Param("active") Integer active,
            @Param("visible") Integer visible,
            @Param("category") Integer category,
            @Param("deposit") Integer deposit,
            @Param("price") Integer price,
            @Param("title") String title,
            @Param("description") String description,
            @Param("tagIds") List<Integer> tagIds,
            @Param("numTags") Integer numTags);
}
