package com.project.RealEstateRental.repositories;

import com.project.RealEstateRental.dtos.PropertyProjection;
import com.project.RealEstateRental.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface PropertiesRepository extends JpaRepository<Properties,Integer> {

    @Query("SELECT COALESCE(MAX(p.idProperty), 0) FROM Properties p")
    long findMaxIdProperty();

    @Modifying
    @Transactional
    @Query("UPDATE Properties p SET p.active = CASE WHEN p.active = 1 THEN 0 ELSE 1 END WHERE p.idProperty = :id")
    int toggleActive(@Param("id") Integer id);

    // Add toggle functionality for the 'visible' field (if needed)
    @Modifying
    @Transactional
    @Query("UPDATE Properties p SET p.visible = CASE WHEN p.visible = 1 THEN 0 ELSE 1 END WHERE p.idProperty = :id")
    int toggleVisible(@Param("id") Integer id);

    @Query("SELECT p FROM Properties p " +
            "WHERE (:idTy IS NULL OR p.type.idType = :idTy) AND " +
            "(:idSt IS NULL OR p.structure.idStructure = :idSt) AND " +
            "(:sqMin IS NULL OR p.squareFootage >= :sqMin) AND " +
            "(:sqMax IS NULL OR p.squareFootage <= :sqMax) AND " +
            "(:idEq IS NULL OR p.equipment.idEquipment = :idEq) AND " +
            "(COALESCE(:idBors, NULL) IS NULL OR p.borough.idBorough IN :idBors) AND " +
            "(p.visible = 1) AND " +
            "(:cat IS NULL OR p.category = :cat) AND " +
            "(:prMin IS NULL OR p.price >= :prMin) AND " +
            "(:prMax IS NULL OR p.price <= :prMax)")
    List<PropertyProjection> findByFilter(
            @Param("idTy") Integer idTy,
            @Param("idSt") Integer idSt,
            @Param("sqMin") Integer sqMin,
            @Param("sqMax") Integer sqMax,
            @Param("idEq") Integer idEq,
            @Param("idBors") List<Integer> idBors,
            @Param("cat") Integer cat,
            @Param("prMin") Integer prMin,
            @Param("prMax") Integer prMax);

    @Query("SELECT p.title AS title, p.rooms AS rooms, p.squareFootage AS squareFootage, " +
            "p.floor AS floor, p.bathrooms AS bathrooms, p.heating AS heating, " +
            "p.deposit AS deposit, p.price AS price, p.description AS description, " +
            "p.thumbnail AS thumbnail, p.active AS active, p.visible AS visible, " +
            "p.category AS category, p.type AS type, p.structure AS structure, " +
            "p.borough AS borough, p.equipment AS equipment " +
            "FROM Properties p WHERE p.idProperty = :id")
    PropertyProjection findProjectedById(@Param("id") Integer id);
}

/*
"(:tagIds IS NULL OR (SELECT COUNT(pt.id) FROM Property_tags pt WHERE pt.property = p AND pt.tag.idTag IN (:tagIds)) = :numTags)")

            @Param("tagIds") List<Integer> tagIds,
            @Param("numTags") Integer numTags);
 */
