package com.project.RealEstateRental.repository;

import com.project.RealEstateRental.model.Pictures;
import com.project.RealEstateRental.model.Properties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PicturesRepository extends JpaRepository<Pictures,Integer> {
    List<Pictures> findByProperty(Properties property);
}
