package com.project.RealEstateRental.repositories;

import com.project.RealEstateRental.models.Equipments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EquipmentsRepository extends JpaRepository<Equipments,Integer> {
}
