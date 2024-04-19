package com.project.RealEstateRental.repository;

import com.project.RealEstateRental.model.Properties;
import org.springframework.data.jpa.repository.JpaRepository;
//TODO Override delete to set invisible :)
public interface PropertiesRepository extends JpaRepository<Properties,Integer> {
}
