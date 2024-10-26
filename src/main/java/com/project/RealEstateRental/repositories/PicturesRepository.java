package com.project.RealEstateRental.repositories;

import com.project.RealEstateRental.dtos.PictureResponse;
import com.project.RealEstateRental.models.Pictures;
import com.project.RealEstateRental.models.Properties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PicturesRepository extends JpaRepository<Pictures,Integer> {

    @Query("SELECT COALESCE(MAX(p.idPicture), 0) FROM Pictures p")
    long findMaxIdPicture();
    List<Pictures> findByProperty(Properties property);

    @Query("SELECT new com.project.RealEstateRental.dtos.PictureResponse(p.pictureName, p.picturePath, p.thumbnailPath) " +
            "FROM Pictures p WHERE p.property = :property ORDER BY p.sequenceNumber")
    List<PictureResponse> findPictureDetailsByPropertyOrdered(Properties property);
}
