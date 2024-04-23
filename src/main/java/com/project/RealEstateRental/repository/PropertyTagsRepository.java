package com.project.RealEstateRental.repository;

import com.project.RealEstateRental.model.Properties;
import com.project.RealEstateRental.model.Property_tags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyTagsRepository extends JpaRepository<Property_tags,Integer> {
    void deleteByProperty(Properties property);
}
