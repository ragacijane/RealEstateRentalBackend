package com.project.RealEstateRental.repositories;

import com.project.RealEstateRental.models.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
@Transactional
public interface TagsRepository extends JpaRepository<Tags,Integer> {
}
