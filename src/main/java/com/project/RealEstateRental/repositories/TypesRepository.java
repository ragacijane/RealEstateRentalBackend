package com.project.RealEstateRental.repositories;

import com.project.RealEstateRental.models.Types;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
@Transactional
public interface TypesRepository extends JpaRepository<Types,Integer> {
}
