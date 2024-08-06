package com.project.RealEstateRental.repositories;

import com.project.RealEstateRental.models.Properties;
import com.project.RealEstateRental.models.Property_tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface PropertyTagsRepository extends JpaRepository<Property_tags,Integer> {
    @Query("SELECT pt.tag.idTag FROM Property_tags pt WHERE pt.property = :property")
    List<Integer> findByProperty(Properties property);
    void deleteByProperty(Properties property);
}
