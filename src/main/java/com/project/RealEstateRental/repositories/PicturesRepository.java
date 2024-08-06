package com.project.RealEstateRental.repositories;

import com.project.RealEstateRental.models.Pictures;
import com.project.RealEstateRental.models.Properties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface PicturesRepository extends JpaRepository<Pictures,Integer> {
    List<Pictures> findByProperty(Properties property);
}
