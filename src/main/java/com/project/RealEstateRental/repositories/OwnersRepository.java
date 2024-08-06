package com.project.RealEstateRental.repositories;

import com.project.RealEstateRental.models.Owners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OwnersRepository extends JpaRepository<Owners,Integer> {
}
