package com.project.RealEstateRental.repositories;
import com.project.RealEstateRental.models.Structures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
@Transactional
public interface StructuresRepository extends JpaRepository<Structures,Integer> {
}
