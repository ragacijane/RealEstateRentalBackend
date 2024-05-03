package com.project.RealEstateRental.repository;

import com.project.RealEstateRental.model.Properties;
import com.project.RealEstateRental.model.Property_tags;
import com.project.RealEstateRental.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PropertyTagsRepository extends JpaRepository<Property_tags,Integer> {
    @Query("SELECT pt.tag.idTag FROM Property_tags pt WHERE pt.property = :property")
    List<Integer> findByProperty(Properties property);
    void deleteByProperty(Properties property);
}
